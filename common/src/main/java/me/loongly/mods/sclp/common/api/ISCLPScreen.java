package me.loongly.mods.sclp.common.api;

public interface ISCLPScreen 
{
    public void open();
    public void close();//为什么不是onClose，因为和Screen的onClose冲突了
}
