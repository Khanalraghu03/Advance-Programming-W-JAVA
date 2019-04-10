package Jeli.Widgets;

public abstract interface JeliButtonCallBack
{
  public abstract void jeliButtonPushed(JeliButton paramJeliButton);
  
  public abstract void jeliButtonGotString(JeliButton paramJeliButton, String paramString);
  
  public abstract void jeliButtonGotInteger(JeliButton paramJeliButton, int paramInt);
  
  public abstract void jeliButtonGotDouble(JeliButton paramJeliButton, double paramDouble);
  
  public abstract String getClassification();
  
  public abstract void setClassification(String paramString);
}
