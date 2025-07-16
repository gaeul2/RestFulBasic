package kr.co.joenconsulting.myrestfulservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import kr.co.joenconsulting.myrestfulservice.bean.AdminUser;
import kr.co.joenconsulting.myrestfulservice.bean.AdminUserV2;
import kr.co.joenconsulting.myrestfulservice.bean.User;
import kr.co.joenconsulting.myrestfulservice.dao.UserDaoService;
import kr.co.joenconsulting.myrestfulservice.exception.UserNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private final UserDaoService userDaoService;
    private UserDaoService service;

    public AdminUserController(UserDaoService service, UserDaoService userDaoService) {
        this.service = service;
        this.userDaoService = userDaoService;
    }

//    @GetMapping("/v1/users/{id}") //url로 버전관리
//    @GetMapping(value="/users/{id}", params="version=1") //파라미터로 버전관리
//    @GetMapping(value="/users/{id}", headers="X-API-VERSION=1") //헤더로 버전관리
    @GetMapping(value="/users/{id}", produces="application/vnd.company.appv1+json") //mime-type/accept header으로 버전관리

    public MappingJacksonValue retrieveUser4Admin(@PathVariable int id){
        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();
        if (user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            //원래는 adminUser.setName(user.getName()); 처럼 일일이 프로퍼티 다 복사해야함
            //BeanUtils의 copyProperties를 이용해서 user에 있는 속성을 adminUser에 복사
            BeanUtils.copyProperties(user, adminUser);
        }

        //필터 작업
        SimpleBeanPropertyFilter filter
                = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");

        //필터는 FilterProvider 형태로 변환하여 사용가능 (- addFilter("대상 -> adminUser의 @JsonFilter 명!!")
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoForAdmin", filter);

        //MappingJacsonValue에 adminUser를 넣어주고, filters(FilterProvider)로 지정해주면 우리가 필터링 한것처럼 매핑됨!
        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filterProvider);

        return mapping;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){
        List<User> allUsers = userDaoService.findAll();

        List<AdminUser> allAdminUserList = new ArrayList<>();
        AdminUser adminUser = null;
        for (User user : allUsers) {
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser);
            allAdminUserList.add(adminUser);
        }

        SimpleBeanPropertyFilter filter =
                SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");

        SimpleFilterProvider filterProvider =
                new SimpleFilterProvider().addFilter("UserInfoForAdmin", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(allAdminUserList);
        mapping.setFilters(filterProvider);

        return mapping;
    }

//    @GetMapping("/v2/users/{id}")
//    @GetMapping(value="/users/{id}", params = "version=2")
//    @GetMapping(value="/users/{id}", headers = "X-API-VERSION=2")
    @GetMapping(value="/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUser4AdminV2(@PathVariable int id){
        User user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();
        if (user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            //원래는 adminUser.setName(user.getName()); 처럼 일일이 프로퍼티 다 복사해야함
            //BeanUtils의 copyProperties를 이용해서 user에 있는 속성을 adminUser에 복사
            BeanUtils.copyProperties(user, adminUser);
            adminUser.setGrade("VIP");
        }

        //필터 작업
        SimpleBeanPropertyFilter filter
                = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate","grade");

        //필터는 FilterProvider 형태로 변환하여 사용가능 (- addFilter("대상 -> adminUser의 @JsonFilter 명!!")
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoForAdminV2", filter);

        //MappingJacsonValue에 adminUser를 넣어주고, filters(FilterProvider)로 지정해주면 우리가 필터링 한것처럼 매핑됨!
        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filterProvider);

        return mapping;
    }
}
