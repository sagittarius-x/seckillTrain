package com.suny.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SuccessKilled implements Serializable {
	private static final long serialVersionUUID = 1834437127882846202L;
	
	private long seckillId;
	
	private long userPhone;
	
	private long state;
	
	private LocalDateTime createTime;
	
	private Seckill seckill;
	
	public SuccessKilled() {
		
	}

	public SuccessKilled(long seckillId, long userPhone, long state, LocalDateTime createTime, Seckill seckill) {
		super();
		this.seckillId = seckillId;
		this.userPhone = userPhone;
		this.state = state;
		this.createTime = createTime;
		this.seckill = seckill;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	public long getState() {
		return state;
	}

	public void setState(long state) {
		this.state = state;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	public static long getSerialversionuuid() {
		return serialVersionUUID;
	}

	@Override
	public String toString() {
		return "SuccessKilled [seckillId=" + seckillId + ", userPhone=" + userPhone + ", state=" + state
				+ ", createTime=" + createTime + ", seckill=" + seckill + "]";
	}
	
}
