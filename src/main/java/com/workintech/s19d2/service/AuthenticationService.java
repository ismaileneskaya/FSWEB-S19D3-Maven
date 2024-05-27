package com.workintech.s19d2.service;

import com.workintech.s19d2.entity.Member;
import com.workintech.s19d2.entity.Role;
import com.workintech.s19d2.repository.MemberRepository;
import com.workintech.s19d2.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthenticationService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public Member register(String email, String password) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            throw new RuntimeException("User with given email already exist" + email);
        }
        String encodePassword = passwordEncoder.encode(password);

        List<Role> roleList = new ArrayList<>();

/*        Optional<Role> roleUser = roleRepository.findByAuthority("Admin");
        if (!roleUser.isPresent()) {
            Role roleUserEntity = new Role();
            roleUserEntity.setAuthority("User");
            roleList.add(roleRepository.save(roleUserEntity));
        } else {
            roleList.add(roleUser.get());
        }
        roleList.add(roleUser.get());
        */

        Optional<Role> roleAdmin = roleRepository.findByAuthority("Admin");
        if (!roleAdmin.isPresent()) {
            Role roleAdminEntity = new Role();
            roleAdminEntity.setAuthority("Admin");
            roleList.add(roleRepository.save(roleAdminEntity));
        } else {
            roleList.add(roleAdmin.get());
        }
        roleList.add(roleAdmin.get());

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(encodePassword);
        member.setRoles(roleList);
        return memberRepository.save(member);

    }
}
