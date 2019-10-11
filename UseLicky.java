class UseLicky.java
{
    private int userId;
    private String userName;
    
    public void setUserId(int userId)
    {
        this.userId = userId;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public void performAction(boolean actionValue)
    {
        if(!actionValue)
        {
            this.userName = null;
        }
        else
        {
            this.userId = 0;
        }
    }
}
