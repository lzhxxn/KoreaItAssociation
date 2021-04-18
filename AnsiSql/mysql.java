-- 공지사항
select notice_id as "글번호", notice_tit as "제목", loginId as "작성자", regdate as "등록일", hit as "조회수" 
  from tb_adm_notice;

-- 수강 상담 이력
select lec_id as "과정 ID", lec_name as "과정명", concat(DATE_FORMAT(c_st, '%Y.%m.%d'), " ~ ", DATE_FORMAT(c_end, '%Y.%m.%d')) as "기간"
  from tb_lec_info;

-- 진도 관리
select week as "주차", learn_con as "내용", learn_goal as "목표" 
  from tb_week_plan
  order by week;

-- 시험 대상자 관리
select li.lec_id
      ,li.lec_name
      ,te.test_name
      ,concat(DATE_FORMAT(te.test_start, '%Y.%m.%d'),'-',DATE_FORMAT(te.test_end, '%Y.%m.%d')) as dduedate 
      ,case when te.test_end < now() then '시험종료'
           else '시험중'
      end state 
      ,ifnull(llc.cnt,0) as cnt
      ,ifnull(llt.cnt,0) as lltcnt
      ,(ifnull(llc.cnt,0) - ifnull(llt.cnt,0)) as nocnt
     -- ,(cnt - lltcnt) as nocnt
  from tb_test te
       inner join tb_lec_info li on te.lec_id = li.lec_id
       left outer join (
                          select lc.lec_id, count(*) as cnt   
                            from tb_lecstd_info lc
                           group by lc.lec_id
                       ) llc on li.lec_id = llc.lec_id
       left outer join (
                          select tu.test_id, count(*) as cnt   
                            from tb_test_user tu
                           group by tu.test_id
                       ) llt on te.test_id = llt.test_id;

-- 이력서 관리
select li.lec_id
     , li.tutor_id 
     , ui.name
     , li.lec_name
     , li.tutor_name
     , li.pre_pnum
     , (select count(*) from tb_lecstd_info ls where ls.lec_id = li.lec_id ) as cnt
     , CONCAT(DATE_FORMAT(li.c_st, '%Y.%m.%d'), ' ~ ', DATE_FORMAT(li.c_end, '%Y.%m.%d')) as cdate
  from tb_lec_info li
       inner join tb_userinfo ui on ui.loginID = li.tutor_id and ui.user_type = 'B';

-- 취업 관리
select ui.name, ui.loginID, ui.tel, ui.mail, ifnull(em.wp_state,'미취업') as wp_state
  from tb_lecstd_info li
       inner join tb_userinfo ui on ui.loginID = li.std_id
       left outer join tb_employ em on li.std_id = em.std_id
 where li.lec_id = '1007';

-- 취업 정보
select ui.name
     , ui.tel
     , em.employ_day
     , em.resign_day  
     , em.comp_name
     , ifnull(em.resign_day,'재직중')                
  from tb_employ em
       inner join tb_userinfo ui on ui.loginID = em.std_id;

-- 취업 정보2
select ui.std_num
      ,ui.name
      ,ui.tel
      ,ll.lec_name
      ,ui.join_date
  from tb_userinfo ui
       left outer join (
                          select li.lec_id
                                ,li.lec_name
                                ,le.std_id
                            from tb_lecstd_info le
                                 inner join tb_lec_info li on li.lec_id = le.lec_id
                       ) ll on ui.loginID = ll.std_id
 where ui.user_type = 'A'
   and ui.loginID not in (
                              select em.std_id
                                from tb_employ em
                         );

-- 강의실
select lm.lecrm_name
      , lm.lecrm_size
      , lm.lecrm_snum
      , lm.lecrm_note
  from tb_lecrm lm;

-- 장비관리
select eq.equ_id
      , eq.equ_name
      , eq.equ_num
      , eq.equ_note
  from tb_equ eq
  where eq.lecrm_id = '1';

-- 테이블끼리 foreign / primary key로 관계와 JOIN은 연관이없었다.
