select *
    from tb_group_code;

select * 
    from tb_detail_code;

select dc.*
    from tb_group_code gc
        ,tb_detail_code dc
    where gc.group_code = dc.group_code;

select gc.group_code, dc.group_code
    from tb_group_code gc
        ,tb_detail_code dc
    where gc.group_code = dc.group_code;  -- inner join

select gc.group_code grouptable, dc.group_code detailtable
    from tb_group_code gc 
       left outer join tb_detail_code dc on gc.group_code = dc.group_code; -- outer join
     
select * 
    from tb_detail_code
    where group_code = 'CDCD';  -- check

select gc.group_code grouptable, dc.group_code detailtable
    from tb_group_code gc 
     right outer join tb_detail_code dc on gc.group_code = dc.group_code; -- outer join
     
select mm.MNU_ID , mm.MNU_NM -- 유저타입이 C인 메뉴명, 메뉴I
    from tn_usr_mnu_atrt ma 
     left outer join tm_mnu_mst mm on ma.MNU_ID = mm.MNU_ID 
    where ma.user_type  = 'C';  

select ll.MNU_ID , ll.MNU_NM -- dbserver 사용자가 사용할 수 있는 메뉴명 (서브쿼리)
    from tb_userinfo ui 
     left outer join (
                         select mm.MNU_ID , mm.MNU_NM, ma.user_type
                           from tm_mnu_mst mm  
                         inner join tn_usr_mnu_atrt ma on ma.MNU_ID = mm.MNU_ID 
                     ) ll on ll.user_type = ui.user_Type 
    where ui.loginId = 'dbserver';
