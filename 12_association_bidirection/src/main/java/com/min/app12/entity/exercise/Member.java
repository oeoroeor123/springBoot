package com.min.app12.entity.exercise;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

// Team - Member
//   1      M    (@ManyToOne + @JoinColumn)

// Member - Locker
//   1        1   (@OneToOne + @JoinColumn)

@Entity(name = "member")
@Table(name = "tbl_member")
public class Member {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Integer memberId;
  
  @Column(name = "member_name")
  private String memberName;
  
  @JoinColumn(name = "team_id")
  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) // Member 조회 시 Team 정보는 join 되지 않습니다.
  private Team team;
  
  @JoinColumn(name = "locker_id")
  @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER) // Member 조회 시 join 을 이용해 Locker 정보를 함께 조회합니다
  private Locker locker;
  
  public Member() {
    // TODO Auto-generated constructor stub
  }

  public Member(Integer memberId, String memberName, Team team, Locker locker) {
    super();
    this.memberId = memberId;
    this.memberName = memberName;
    this.team = team;
    this.locker = locker;
  }

  @Override
  public String toString() {
    return "Member [memberId=" + memberId + ", memberName=" + memberName + ", team=" + team + ", locker=" + locker
        + "]";
  }

  public Integer getMemberId() {
    return memberId;
  }

  public void setMemberId(Integer memberId) {
    this.memberId = memberId;
  }

  public String getMemberName() {
    return memberName;
  }

  public void setMemberName(String memberName) {
    this.memberName = memberName;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public Locker getLocker() {
    return locker;
  }

  public void setLocker(Locker locker) {
    this.locker = locker;
  }
 
}
