package project.blobus.Backend.member.member.general.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.member.basic.util.ModelMapper;
import project.blobus.Backend.member.member.general.dto.GeneralDTO;
import project.blobus.Backend.member.member.general.entity.GeneralMember;
import project.blobus.Backend.member.member.general.repository.GeneralRepository;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GeneralService {
    private final GeneralRepository generalRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public Long register(GeneralDTO generalDTO) {
        log.info("GeneralMember Register");

        GeneralMember generalMember = ModelMapper.generalDtoToEntity(generalDTO);
        generalMember.setUserPw(passwordEncoder.encode(generalDTO.getUserPw()));
        generalMember.setJoinDate(LocalDate.now());
        generalRepository.save(generalMember);

        return generalMember.getId();
    }

    // 회원가입 - 아이디 중복
    public boolean duplicate(String userId) {
        log.info("GeneralMember Duplicate");

        // userId로 GeneralMember 찾기
        Optional<GeneralMember> optionalGeneralMember = generalRepository.findByUserId(userId);

        // userId가 존재하면 true 반환, 존재하지 않으면 false 반환
        return optionalGeneralMember.isPresent();
    }

//    public EmployeeDTO get(Long enb) {
//
//        Employee employee = employeeRepository.findById(enb).orElseThrow();
//        EmployeeDTO employeeDTO = entityToDTO(employee);
//        return employeeDTO;
//    }

    // 회원정보 수정
//    public void modify(EmployeeDTO employeeDTO) {
//        log.info("modify");
//
//        Optional<Employee> employeeOpt = employeeRepository.findById(employeeDTO.getEnb());
//        if (employeeOpt.isPresent()) {
//            Employee employee = employeeOpt.orElseThrow();
//
//            if (employeeDTO.getEmpPW() != null && employeeDTO.getEmpPW() != employee.getEmpPW())
//                employee.setEmpPW(passwordEncoder.encode(employeeDTO.getEmpPW()));
//            if (employeeDTO.getHireDate() != null) {
//                employee.setHireDate(employeeDTO.getHireDate());
//                employee.setEmpID(EmployeeIDGenerator.generateEmployeeID(employeeDTO.getEnb(), employeeDTO.getHireDate()));
//            }
//            if (employeeDTO.getName() != null) employee.setName(employeeDTO.getName());
//            if (employeeDTO.getBirthDate() != null) employee.setBirthDate(employeeDTO.getBirthDate());
//            if (employeeDTO.isGender() != employee.isGender()) employee.setGender(employeeDTO.isGender());
//            if (employeeDTO.getPhoneNum() != 0) employee.setPhoneNum(employeeDTO.getPhoneNum());
//            if (employeeDTO.getEmail() != null) employee.setEmail(employeeDTO.getEmail());
//            if (employeeDTO.getCompanyDTO() != null) {
//                employee.setCompany(companyRepository.findById(employeeDTO.getCompanyDTO().getCnb()).orElseThrow());
//            }
//            employeeRepository.save(employee);
//        } else {
//            throw new EntityNotFoundException("수정할 정보를 찾을 수 없습니다.");
//        }
//    }

    // 회원 탈퇴
//    public void remove(Long enb) {
//        log.info("remove");
//
//        Optional<Employee> employeOpt = employeeRepository.findById(enb);
//        if (employeOpt.isPresent()) {
//            Employee employee = employeOpt.orElseThrow();
//
//            employee.setEnb(enb);
//            employee.setEmpPW(null);
//            employee.setBirthDate(null);
//            employee.setEmail(null);
//            employee.setDelFlag(true);
//            employeeRepository.save(employee);
//        } else {
//            throw new EntityNotFoundException("삭제할 정보를 찾을 수 없습니다.");
//        }
//    }
}
