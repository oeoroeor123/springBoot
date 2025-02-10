package com.min.app12.entity.exercise;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "locker")
@Table(name = "tbl_locker")
public class Locker {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "locker_id")
  private Integer lockerId;
  
  @Column(name = "locker_name")
  private String lockerName;
  
  // 생성자 주입 1
  public Locker() {
    // TODO Auto-generated constructor stub
  }
  
  // 생성자 주입 1
  public Locker(Integer lockerId, String lockerName) {
    super();
    this.lockerId = lockerId;
    this.lockerName = lockerName;
  }

  // toString
  @Override
  public String toString() {
    return "Locker [lockerId=" + lockerId + ", lockerName=" + lockerName + "]";
  }

  // getter, setter
  public Integer getLockerId() {
    return lockerId;
  }

  public void setLockerId(Integer lockerId) {
    this.lockerId = lockerId;
  }

  public String getLockerName() {
    return lockerName;
  }

  public void setLockerName(String lockerName) {
    this.lockerName = lockerName;
  }
  
}
