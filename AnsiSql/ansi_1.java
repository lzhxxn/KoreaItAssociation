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


----------------------------------------------------------------- 2day ---------------------------------------------------------------------
select * from tm_mnu_mst;
select * from tn_usr_mnu_atrt; -- 메뉴 권한
select * from tb_userinfo;

-- 유저 타입이 C 인 메뉴명, 메뉴 ID
select mm.MNU_ID, mm.MNU_NM
  from tn_usr_mnu_atrt ma -- 데이터가 일치하기 때문에 inner join 변경해도 같다.
      inner join tm_mnu_mst mm on ma.MNU_ID = mm.MNU_ID and ma.user_type = 'C'; -- on 뒤에 and로 조건을 걸어준다. 이중 조건 
 -- where ~

-- 유저 타입이 C 
-- 모든 쿼리문 작성시 //   * from 절 -> join -> where -> 추출 할 컬럼 순으로 작성하기 !
select * 
  from tn_usr_mnu_atrt ma
       left outer join tm_mnu_mst mm on ma.MNU_ID = mm.MNU_ID
 where ma.user_type = 'C';
 
-- dbserver 사용자가 사용할 수 있는 메뉴명
select ll.MNU_ID , ll.MNU_NM
  from tb_userinfo ui
       left outer join(
                        select mm.MNU_ID , mm.MNU_NM, ma.user_type
                          from tn_usr_mnu_atrt ma
                          inner join tm_mnu_mst mm on ma.MNU_ID = mm.MNU_ID  -- mm Alias는 서브쿼리 안에서만 통용된다.
                       ) ll on ll.user_type = ui.user_type
  where ui.loginId = 'dbserver';
  
-- 같은 개선된 쿼리문
select mm.MNU_ID, mm.MNU_NM, ma.user_type
  from tn_usr_mnu_atrt ma -- 데이터가 일치하기 때문에 inner join 변경해도 같다.
      inner join tm_mnu_mst mm on ma.MNU_ID = mm.MNU_ID 
                              and ma.user_type in (
                                                    select user_type
                                                      from tb_userinfo
                                                      where loginId = 'dbserver'
                                                   );
-- from 절에 서브쿼리 쓰는게 가장 좋다.
-- <Query 순서>
-- 1. select
-- 2. from
-- 3. where
-- 4. group by  -> group by A, B   함수 ( max() min() avg() sum() )
-- 5. order by
-- 6. having    -> 그룹함수 결과에 검색 조건을 걸 때 
-- * group by 에 있는 부분이 select 하는 데이터 값에 꼭 포함되어있어야 한다. 예) 그룹 지어진 부서별 평균 급여가 2000 이상인 부서의 번호와 부서별 평균 급여를 출력하는 쿼리문
-- SQL> SELECT DEPTNO, AVG(SAL) FROM EMP GROUP BY DEPTNO;
-- SQL> SELECT DEPTNO, AVG(SAL) FROM EMP GROUP BY DEPTNO HAVING AVG(SAL) >= 2000;


