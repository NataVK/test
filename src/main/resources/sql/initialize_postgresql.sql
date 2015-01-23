CREATE SEQUENCE ids_seq;
ALTER TABLE itemmeasure ALTER COLUMN id SET DEFAULT NEXTVAL('ids_seq');

insert into itemmeasure(name) values('кг');
insert into itemmeasure(name) values('шт');
insert into itemmeasure(name) values('м');
insert into itemmeasure(name) values('м2');
insert into itemmeasure(name) values('м3');

CREATE OR REPLACE FUNCTION do_order_deleted()
  RETURNS trigger AS '
DECLARE
BEGIN
  delete from itemorderinfo where orderid = old.id;
  return old;
END;
' LANGUAGE 'plpgsql'
VOLATILE;

CREATE OR REPLACE FUNCTION do_item_updated()
  RETURNS trigger AS '
DECLARE
BEGIN
  NEW.updated = now();
  return NEW;
END;
' LANGUAGE 'plpgsql'
VOLATILE;

CREATE OR REPLACE FUNCTION do_order_updated()
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
  update orders set updated = now() where id = var_orderid;
  IF (TG_OP = ''DELETE'')
  THEN
    return OLD;
  ELSE
    return NEW;
  END IF;
END;
' LANGUAGE 'plpgsql'
VOLATILE;

CREATE TRIGGER item_trg_bfr_updt BEFORE UPDATE
ON item FOR EACH ROW
WHEN (new.measure != old.measure
      or new.name != old.name
      or new.price != old.price)
EXECUTE PROCEDURE do_item_updated();

CREATE TRIGGER orders_trg_aft_del AFTER DELETE
ON orders FOR EACH ROW
EXECUTE PROCEDURE do_order_deleted();

CREATE TRIGGER itemorderinfo_trg_aft_all AFTER INSERT OR UPDATE OR DELETE
ON itemorderinfo FOR EACH ROW
EXECUTE PROCEDURE do_order_updated();

--drop TRIGGER item_trg_aft_updt ON item;
--DROP FUNCTION do_item_updated();
