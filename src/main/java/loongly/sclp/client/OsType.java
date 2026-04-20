package loongly.sclp.client;

public enum OsType 
{
    WINDOWS,
    MACOS,
    LINUX,
    OTHER;

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    public static OsType getCurrentOs() 
    {
        if (OS_NAME.contains("win")) 
        {
            return WINDOWS;
        } 
        else if (OS_NAME.contains("mac") || OS_NAME.contains("darwin")) 
        {
            return MACOS;
        }
         else if (OS_NAME.contains("linux") || OS_NAME.contains("nix") || OS_NAME.contains("nux") || OS_NAME.contains("aix")) 
        {
            return LINUX;
        } 
        else 
        {
            return OTHER;
        }
    }
    
    @Override
    public String toString() {
        return this.name() + " (" + OS_NAME + ")";
    }
}
