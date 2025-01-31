package com.min.app06.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.min.app06.mapper.IMemberMapper;
import com.min.app06.model.dto.AddrDto;
import com.min.app06.model.dto.MemberDto;
import com.min.app06.util.PageUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements IMemberService {
  
  private final IMemberMapper memberMapper;
  private final PageUtil pageUtil;
  
  @Override
  public Map<String, Object> getMembers(HttpServletRequest request) {
    int page = Integer.parseInt(request.getParameter("page"));
    int display = Integer.parseInt(request.getParameter("display"));
    int count = memberMapper.selectMemberCount();
    pageUtil.setPaging(page, display, count);
    List<MemberDto> memberList = memberMapper.selectMemberList(Map.of("offset", pageUtil.getOffset(), "display", display));
    return Map.of("count", count
                , "memberList", memberList
                , "paging", pageUtil.getAsyncPaging(Map.of("display", display)));
  }

  @Override
  public Map<String, Object> getMemberById(int memId) {
    AddrDto addrDto =  memberMapper.selectAddressById(memId);
    return Map.of("member", memberMapper.selectMemberById(memId)
               , "address", addrDto == null ? new AddrDto() : addrDto);
  }
  
  @Override
  public Map<String, Object> registMember(Map<String, Object> params) {
    MemberDto memberDto = MemberDto.builder()
                            .memEmail(params.get("memEmail").toString())
                            .memName(params.get("memName").toString())
                            .memGender(params.get("memGender").toString())
                          .build();
    memberMapper.insertMember(memberDto); // 매퍼의 useGeneratedKeys와 keyProperty에 의해서 memId에 자동 생성된 키 값(AUTO_INCREMENT)이 저장된다.
    AddrDto addrDto = AddrDto.builder()
                        .postcode(params.get("postcode").toString())
                        .roadAddress(params.get("roadAddress").toString())
                        .jibunAddress(params.get("jibunAddress").toString())
                        .detailAddress(params.get("detailAddress").toString())
                        .extraAddress(params.get("extraAddress").toString())
                        .memId(memberDto.getMemId())
                        .addrName(params.get("addrName").toString())
                      .build();
    memberMapper.insertAddress(addrDto);
    return Map.of("member", memberDto, "address", addrDto);
  }
  
  @Override
  public Map<String, Object> modifyMember(Map<String, Object> params) {
  MemberDto memberDto = MemberDto.builder()
          .memId((int)params.get("memId"))
          .memName(params.get("memName").toString())
          .memGender(params.get("memGender").toString())
        .build();
  memberMapper.updateMember(memberDto);
  
  AddrDto addrDto = AddrDto.builder()
        .postcode(params.get("postcode").toString())
        .roadAddress(params.get("roadAddress").toString())
        .jibunAddress(params.get("jibunAddress").toString())
        .detailAddress(params.get("detailAddress").toString())
        .extraAddress(params.get("extraAddress").toString())
        .addrName(params.get("addrName").toString())
      .build();
  // addrId가 0이면 insert로 추가
  if((int)params.get("addrId") == 0) {
      addrDto.setMemId((int)params.get("memId"));
      memberMapper.insertAddress(addrDto);
    } else {
      // addrId가 0이 아니면, update로 변경
      addrDto.setAddrId((int)params.get("addrId"));
      memberMapper.updateAddress(addrDto);
    }
  return Map.of("member", memberDto, "address", addrDto);
    }
  
  @Override
  public int removeMember(int memId) {
    return memberMapper.delectMember(memId);
  }
  
  @Override
  public int removeSelectMember(String memIds) {
    return memberMapper.delectSelectMember(memIds);
  }

}
