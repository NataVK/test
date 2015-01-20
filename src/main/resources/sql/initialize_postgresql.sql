CREATE SEQUENCE ids_seq;
ALTER TABLE itemmeasure ALTER COLUMN id SET DEFAULT NEXTVAL('ids_seq');

insert into itemmeasure(name) values('кг');
insert into itemmeasure(name) values('шт');
insert into itemmeasure(name) values('м');
insert into itemmeasure(name) values('м2');
insert into itemmeasure(name) values('м3');

CREATE OR REPLACE FUNCTION count_order_total_prices()
RETURNS trigger AS '
DECLARE
  var_orderid bigint;
BEGIN
  IF (TG_OP = ''DELETE'')
  THEN
    var_orderid := OLD.orderid;
  ELSE
    var_orderid := NEW.orderid;
  END IF;
  update public.orders
  set totalprice = (SELECT
                      SUM(price)
                    FROM public.itemorderinfo
                    where orderid = var_orderid),
    updated = now()
  where id = var_orderid;
  IF (TG_OP = ''DELETE'')
  THEN
    return OLD;
  ELSE
    return NEW;
  END IF;
END;
' LANGUAGE 'plpgsql'
VOLATILE;

CREATE TRIGGER trg_update_order AFTER INSERT OR UPDATE OR DELETE
ON public.itemorderinfo FOR EACH ROW
EXECUTE PROCEDURE count_order_total_prices();

CREATE OR REPLACE FUNCTION do_item_updated()
  RETURNS trigger AS '
DECLARE
BEGIN
  NEW.updated = now();
  return NEW;
END;
' LANGUAGE 'plpgsql'
VOLATILE;

CREATE TRIGGER item_trg_aft_updt AFTER UPDATE
ON item FOR EACH ROW
EXECUTE PROCEDURE do_item_updated();
