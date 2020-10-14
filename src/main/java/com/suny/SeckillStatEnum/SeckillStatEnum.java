package com.suny.SeckillStatEnum;

public enum SeckillStatEnum {
    SUCCESS(1, "seckill Success"),
    END(0, "seckill End"),
    REPEAT_KILL(-1, "Repeat seckill"),
    INNER_ERROR(-2, "System issue"),
    DATE_REWRITE(-3, "Data rewrite");

    private int state;
    private String info;

    SeckillStatEnum(){

    }

    SeckillStatEnum(int state, String info){
        this.state = state;
        this.info = info;
    }

    public int getState(){
        return state;
    }

    public String getInfo(){
        return info;
    }

    public static SeckillStatEnum stateOf(int index){
        for(SeckillStatEnum statEnum : values()){
            if(statEnum.getState() == index){
                return statEnum;
            }
        }
        return null;
    }
}
