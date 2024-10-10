package com.nhnacademy.daily.controller;

import com.nhnacademy.daily.model.MemberCreateCommand;
import com.nhnacademy.daily.service.MemberService;
import com.opencsv.CSVReader;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // CSV 형식의 멤버 생성 API
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_CSV_VALUE})
    public ResponseEntity<?> createMember(@RequestBody List<MemberCreateCommand> memberCreateCommands,
                                          @RequestHeader("Content-Type") String contentType) {
        memberCreateCommands.forEach(memberService::createMember);
        return ResponseEntity.ok("Members created successfully");
    }

    // CSV 데이터를 파싱
    @PostMapping(path = "/csv", consumes = "text/csv")
    public ResponseEntity<?> createMembersFromCSV(HttpServletRequest request) throws Exception {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(request.getInputStream()))) {
            List<String[]> lines = csvReader.readAll();

            String[] headers = lines.get(0); // 첫 번째 줄은 헤더
            List<MemberCreateCommand> members = lines.stream().skip(1).map(line -> {
                MemberCreateCommand member = new MemberCreateCommand();
                for (int i = 0; i < headers.length; i++) {
                    switch (headers[i].toLowerCase()) {
                        case "id": member.setId(line[i]); break;
                        case "name": member.setName(line[i]); break;
                        case "password": member.setPassword(line[i]); break;
                    }
                }
                return member;
            }).collect(Collectors.toList());

            memberService.createMembers(members);
            return ResponseEntity.ok("CSV Members created successfully");
        }
    }
}
