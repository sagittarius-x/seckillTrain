package com.suny.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Seckill implements Serializable {
	
	private static final long serialVersionUID = 2912164127598660137L;
	
	private long seckillId;
	
	private String name;
	
	private int number;
	
	private LocalDateTime startTime;
	
	private LocalDateTime endTime;
	
	private LocalDateTime createTime;
	
	public Seckill() {
		
	}
	
	public Seckill(long seckillId, String name, int number, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime createTIme) {
        this.seckillId = seckillId;
        this.name = name;
        this.number = number;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createTime = createTIme;
    }

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Seckill [seckillId=" + seckillId + ", name=" + name + ", number=" + number + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", createTime=" + createTime + "]";
	}
}
