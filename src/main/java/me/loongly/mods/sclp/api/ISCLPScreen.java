package me.loongly.mods.sclp.api;

public interface ISCLPScreen 
{
    public void open();
    public void close();//其实onClose没问题，因为这里的MixinSodiumEmbeddiumOptionGUI没有继承Screen，所有不会冲突。但是为了统一，也改成close
}
