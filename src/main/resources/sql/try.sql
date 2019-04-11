update user
set surname      = 'test',
    name         = 'test',
    patronymic   = 'test',
    login        = 'test',
    password     = 'test',
    user_group   = 1,
    phone_number = 'test',
    email        = 'test'
where id = 1;

use
repaircenter;


//
выбираем
устройства
со
статусом
ремонта
Принято
select device.id,
       type.value,
       brand.value,
       model.value,
       serial_number,
       defect.value,
       owner_id,
       repair_id,
       status.value,
       completeness.value,
       appearance.value,
       note
from device
       inner join type on device.type_id = type.id
       inner join brand on device.brand_id = brand.id
       inner join model on device.model_id = model.id
       inner join defect on device.defect_id = defect.id
       inner join completeness on device.completeness_id = completeness.id
       inner join appearance on device.appearance_id = appearance.id
       inner join repair on device.repair_id = repair.id
       inner join status on repair.status_id = status.id
WHERE status.value = 'Принято';

select device.id,
       t.value,
       b.value,
       m.value,
       device.serial_number,
       d.value,
       device.owner_id,
       s.value,
       c.value,
       a.value,
       device.note
from device
       inner join type t on device.type_id = t.id
       inner join brand b on device.brand_id = b.id
       inner join model m on device.model_id = m.id
       inner join defect d on device.defect_id = d.id
       inner join completeness c on device.completeness_id = c.id
       inner join appearance a on device.appearance_id = a.id
       inner join repair rep on device.repair_id = rep.id
       inner join status s on rep.status_id = s.id
WHERE s.value = 'Диагностика';

select device.id,
       t.value,
       b.value,
       m.value,
       device.serial_number,
       d.value,
       device.owner_id,
       s.value,
       c.value,
       a.value,
       device.note
from device
       inner join type t on device.type_id = t.id
       inner join brand b on device.brand_id = b.id
       inner join model m on device.model_id = m.id
       inner join defect d on device.defect_id = d.id
       inner join completeness c on device.completeness_id = c.id
       inner join appearance a on device.appearance_id = a.id
       inner join repair rep on device.repair_id = rep.id
       inner join status s on rep.status_id = s.id
       inner join user u on rep.master_id = u.id
WHERE rep.date_of_receipt = '16-01-2019'
  and u.login = 'hodor';

SELECT device.id, t.value, rep.acceptor_id, s.value
FROM device
       inner join type t on device.type_id = t.id
       inner join repair rep on device.repair_id = rep.id
       inner join status s on rep.status_id = s.id
WHERE s.value = 'Ожидание комплектующих';

select id, date_of_receipt
from repair
where date_of_receipt = "13-01-2019";

SELECT user.id,
       user.login,
       user.password,
       user_group.value,
       user.surname,
       user.name,
       user.patronymic,
       user.phone_number,
       user.email
FROM user
       INNER JOIN user_group ON user.user_group = user_group.id
where user_group.value not like 'Уволен';

select
  device.id,
  type.value,
  brand.value,
  model.value,
  device.serial_number,
  defect.value,
  device.owner_id,
  status.value,
  completeness.value,
  appearance.value,
  device.note,
  repair.id,
  repair.date_of_receipt,
  repair.master_comments,
  repair.diagnostic_result
from device
       inner join type on device.type_id = type.id
       inner join brand on device.brand_id = brand.id
       inner join model on device.model_id = model.id
       inner join defect on device.defect_id = defect.id
       inner join completeness on device.completeness_id = completeness.id
       inner join appearance on device.appearance_id = appearance.id
       inner join repair on device.repair_id = repair.id
       inner join status on repair.status_id = status.id
       inner join user on repair.master_id = user.id
WHERE status.value = 'Диагностика'
  and user.login = 's3virge';

update user
set surname = 'test', name = 'test', patronymic = 'test', login = 'test', password = 'test', user_group = 1, phone_number = 'test', email = 'test'
where id = 1;

use repaircenter;

select device.id, t.value, b.value, m.value, device.serial_number,
d.value, device.owner_id, s.value, c.value, a.value, device.note
from device
inner join type t on device.type_id = t.id
inner join brand b on device.brand_id = b.id
inner join model m on device.model_id = m.id
inner join defect d on device.defect_id = d.id
inner join completeness c on device.completeness_id = c.id
inner join appearance a on device.appearance_id = a.id
inner join repair rep on device.repair_id = rep.id
inner join status s on rep.status_id = s.id
WHERE s.value = 'Принято';

select device.id, type.value, brand.value, model.value, serial_number,
       defect.value, owner_id, repair_id, status.value, completeness.value, appearance.value, note
from device
       inner join type on device.type_id = type.id
       inner join brand on device.brand_id = brand.id
       inner join model on device.model_id = model.id
       inner join defect on device.defect_id = defect.id
       inner join completeness on device.completeness_id = completeness.id
       inner join appearance on device.appearance_id = appearance.id
       inner join repair on device.repair_id = repair.id
       inner join status on repair.status_id = status.id
WHERE status.value = 'Принято';

select device.id, t.value, b.value, m.value, device.serial_number,
d.value, device.owner_id, s.value, c.value, a.value, device.note
from device
inner join type t on device.type_id = t.id
inner join brand b on device.brand_id = b.id
inner join model m on device.model_id = m.id
inner join defect d on device.defect_id = d.id
inner join completeness c on device.completeness_id = c.id
inner join appearance a on device.appearance_id = a.id
inner join repair rep on device.repair_id = rep.id
inner join status s on rep.status_id = s.id
WHERE s.value = 'Диагностика';

SELECT device.id, t.value, rep.acceptor_id, s.value
FROM device
    inner join type t on device.type_id = t.id
inner join repair rep on device.repair_id = rep.id
inner join status s on rep.status_id = s.id
WHERE s.value = 'Ожидание комплектующих';

select device.id, type.value, brand.value,
				 model.value ,
				 device.serial_number,
				 defect.value ,
				 device.owner_id,
				 status.value ,
				 completeness.value,
				 appearance.value ,
				 device.note ,
				 repair.id ,
				 repair.date_of_receipt,
				 repair.master_comments ,
                 repair.diagnostic_result,
                 repair.repair_result
				 from device
				 inner join type on device.type_id = type.id
				 inner join brand on device.brand_id = brand.id
				 inner join model on device.model_id = model.id
				 inner join defect on device.defect_id = defect.id
				 inner join completeness on device.completeness_id = completeness.id
				 inner join appearance on device.appearance_id = appearance.id
				 inner join repair on device.repair_id = repair.id
				 inner join status on repair.status_id = status.id
				 inner join user on repair.master_id = user.id
				 WHERE status.value = 'Готово'  ;