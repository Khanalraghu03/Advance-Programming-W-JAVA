package Jeli;

import Jeli.Widgets.JeliButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Point;
import java.util.Vector;

public class Utility
{
  private static String defaultRemoteHost = "vip.cs.utsa.edu";
  private static int defaultRemotePort = 20010;
  public static Jeli.Widgets.HelpManager hm;
  private static java.net.URL backURL = null;
  private static int remotePort = 0;
  private static String applicationName = "unknown";
  private static String applicationVersion = "unknown";
  private static String applicationUpdate = "unknown";
  private static Vector buttonLinks = null;
  private static Vector pushedButtonLinks = null;
  private static Vector refreshables = null;
  private static boolean linksEnabled = true;
  private static Vector linkChangeList;
  private static Vector visibleComponentList;
  private static Jeli.Logging.StandardLogger logger;
  private static Jeli.Logging.StandardLogButtons slb = null;
  private static Jeli.Widgets.JeliTextFrame infoFrame = null;
  private static boolean infoVerbose = false;
  private static String[] allruns;
  private static String[] allexps;
  private static Vector internalFiles;
  private static Jeli.Logging.EmailSender es;
  private static int defaultFontSize = 10;
  private static String defaultFontFamily = "Dialog";
  public static JeliRedirectorMaster redirector = null;
  public static Color jeliMediumRed = new Color(255, 102, 102);
  public static Color jeliLightRed = new Color(255, 204, 204);
  public static Color jeliVeryLightRed = new Color(255, 240, 240);
  public static Color jeliMediumGreen = new Color(102, 255, 102);
  
  public Utility() {}
  
  public static Color jeliLightGreen = new Color(204, 255, 204);
  
  public static Color jeliVeryLightGreen = new Color(240, 255, 240);
  public static Color jeliMediumBlue = new Color(102, 102, 255);
  public static Color jeliLightBlue = new Color(204, 204, 255);
  public static Color jeliVeryLightBlue = new Color(240, 240, 255);
  public static Color jeliMediumCyan = new Color(102, 255, 255);
  public static Color jeliLightCyan = new Color(240, 255, 255);
  public static Color jeliVeryLightCyan = new Color(240, 255, 255);
  public static Color jeliMediumMagenta = new Color(255, 102, 255);
  public static Color jeliLightMagenta = new Color(255, 204, 255);
  public static Color jeliVeryLightMagenta = new Color(255, 240, 255);
  public static Color jeliMediumYellow = new Color(255, 255, 102);
  public static Color jeliLightYellow = new Color(255, 255, 204);
  public static Color jeliVeryLightYellow = new Color(255, 255, 240);
  public static Color jeliMediumGray = new Color(102, 102, 102);
  public static Color jeliLightGray = new Color(204, 204, 204);
  public static Color jeliVeryLightGray = new Color(240, 240, 240);
  public static int[] colorDegrees = { 0, 102, 204, 240 };
  public static final int CD_FULL = 0;
  public static final int CD_MEDIUM = 1;
  public static final int CD_LIGHT = 2;
  public static final int CD_VERYLIGHT = 3;
  public static final int SCT_GRAY = 0;
  public static final int SCT_RED = 1;
  public static final int SCT_GREEN = 2;
  public static final int SCT_BLUE = 4;
  public static final int SCT_YELLOW = 3;
  public static final int SCT_CYAN = 6;
  public static final int SCT_MAGENTA = 5;
  
  public static Color[][] standardColors = (Color[][])null;
  private static String appVersionString = "";
  private static JeliButton startButton = null;
  private static JeliButton infoButton = null;
  private static Vector[][] standardColorComponents;
  
  private static JeliBounds lastFrameDisplayed = null;
  private static JeliBounds lastFrameDisplayedTop = null;
  public static boolean useAudio = true;
  private static JeliApplicationVersion appVersion = null;
  private static Jeli.Animation.VirtualTime vt = null;
  private static String configFilename = null;
  private static String configDirectory = "";
  private static String configSeparator = "";
  
  private static JeliMessenger messenger = null;
  
  public static String getFullConfigFilename() {
    if (configFilename == null)
      return null;
    return configDirectory + configFilename;
  }
  
  public static String getConfigFilename() {
    return configFilename;
  }
  
  public static String getConfigDirectory() {
    return configDirectory;
  }
  
  public static String getConfigSeparator() {
    return configSeparator;
  }
  
  public static String getFullFilename(String fn) {
    return configDirectory + fn;
  }
  
  public static String getFullFilename(String fn, String dir) {
    if ((dir.startsWith("/")) || (dir.indexOf(":") == 1)) {
      if ((dir.endsWith("/")) || (dir.endsWith("\\")))
        return dir + fn;
      return dir + configSeparator + fn;
    }
    if ((dir.endsWith("/")) || (dir.endsWith("\\")))
      return dir + configDirectory + fn;
    return dir + configSeparator + configDirectory + fn;
  }
  
  public static void useVirtualTime(Jeli.Animation.VirtualTime vt1) {
    vt = vt1;
  }
  
  public static Jeli.Animation.VirtualTime getVirtualTime() {
    return vt;
  }
  
  public static String getDefaultRemoteHost() {
    return defaultRemoteHost;
  }
  
  public static int getDefaultRemotePort() {
    return defaultRemotePort;
  }
  
  public static void addRefreshable(Jeli.Widgets.JeliRefreshable r) {
    if (refreshables == null)
      refreshables = new Vector();
    for (int i = 0; i < refreshables.size(); i++)
      if (refreshables.elementAt(i) == r)
        return;
    refreshables.add(r);
  }
  
  public static void refreshAll() {
    if (refreshables == null)
      return;
    for (int i = 0; i < refreshables.size(); i++)
      ((Jeli.Widgets.JeliRefreshable)refreshables.elementAt(i)).refresh();
  }
  
  public static void setUseAudio(boolean f) {
    useAudio = f;
  }
  
  public static void playAudioClip(java.applet.AudioClip ac) {
    if (!useAudio)
      return;
    if (ac == null)
      return;
    ac.play();
  }
  
  public static void setRedirector() {
    redirector = new JeliRedirectorMaster();
  }
  
  public static void setAppVersionString(String s) {
    appVersionString = s;
  }
  
  public static String getAppVersionString() {
    return appVersionString;
  }
  
  public static void setupStandardColorsIfNecessary() {
    if (standardColors == null)
      setupStandardColors();
  }
  
  public static void setColorDegrees(int d0, int d1, int d2, int d3) {
    colorDegrees[0] = d0;
    colorDegrees[1] = d1;
    colorDegrees[2] = d2;
    colorDegrees[3] = d3;
  }
  
  public static void setupStandardColors() {
    standardColors = new Color[7][4];
    standardColorComponents = new Vector[7][4];
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 4; j++) {
        standardColorComponents[i][j] = new Vector();
        standardColors[i][j] = new Color(typeToRed(i, colorDegrees[j]), typeToGreen(i, colorDegrees[j]), typeToBlue(i, colorDegrees[j]));
      }
    }
    
    setColorNames();
  }
  
  private static void setColorNames() {
    jeliMediumRed = standardColors[1][1];
    jeliLightRed = standardColors[1][2];
    jeliVeryLightRed = standardColors[1][3];
    jeliMediumGreen = standardColors[2][1];
    jeliLightGreen = standardColors[2][2];
    jeliVeryLightGreen = standardColors[2][3];
    jeliMediumBlue = standardColors[4][1];
    jeliLightBlue = standardColors[4][2];
    jeliVeryLightBlue = standardColors[4][3];
    jeliMediumCyan = standardColors[6][1];
    jeliLightCyan = standardColors[6][2];
    jeliVeryLightCyan = standardColors[6][3];
    jeliMediumMagenta = standardColors[5][1];
    jeliLightMagenta = standardColors[5][2];
    jeliVeryLightMagenta = standardColors[5][3];
    jeliMediumYellow = standardColors[3][1];
    jeliLightYellow = standardColors[3][2];
    jeliVeryLightYellow = standardColors[3][3];
  }
  
  public static void resetStandardColors() {
    for (int i = 0; i < 7; i++)
      for (int j = 0; j < 4; j++) {
        standardColors[i][j] = new Color(typeToRed(i, colorDegrees[j]), typeToGreen(i, colorDegrees[j]), typeToBlue(i, colorDegrees[j]));
        

        resetStandardComponentColors(i, j);
      }
    setColorNames();
  }
  
  public static void setBackground(Component com, int type, int degree) {
    if (standardColors == null)
      return;
    if ((type < 0) || (type >= 7) || (degree < 0) || (degree >= 4))
      return;
    com.setBackground(standardColors[type][degree]);
  }
  
  private static int typeToRed(int type, int val) {
    return (type & 0x1) != 0 ? 255 : val;
  }
  
  private static int typeToGreen(int type, int val) {
    return (type & 0x2) != 0 ? 255 : val;
  }
  
  private static int typeToBlue(int type, int val) {
    return (type & 0x4) != 0 ? 255 : val;
  }
  
  public static void setStandardColor(int type, int degree, int red, int green, int blue)
  {
    if ((type < 0) || (type >= 7) || (degree < 0) || (degree >= 4))
      return;
    standardColors[type][degree] = new Color(red, green, blue);
    setColorNames();
  }
  
  public static int getValueFromColor(Color c, int type) {
    if ((type & 0x1) == 0) return c.getRed();
    if ((type & 0x2) == 0) return c.getGreen();
    return c.getBlue();
  }
  
  public static void resetStandardComponentColors(int type, int degree)
  {
    if ((type < 0) || (type >= 7) || (degree < 0) || (degree >= 4))
      return;
    for (int i = 0; i < standardColorComponents[type][degree].size(); i++) {
      Component com = (Component)standardColorComponents[type][degree].elementAt(i);
      com.setBackground(standardColors[type][degree]);
    }
  }
  
  public static void resetStandardComponentColors() {
    if (hm != null)
      hm.resetButtonBackgrounds();
  }
  
  public static String getColoredObjects(int type, int degree, String s) {
    String str = "";
    if ((type < 0) || (type >= 7) || (degree < 0) || (degree >= 4))
      return str;
    if (standardColorComponents == null)
      return str;
    for (int i = 0; i < standardColorComponents[type][degree].size(); i++) {
      str = str + s + standardColorComponents[type][degree].elementAt(i) + "\n";
    }
    return str;
  }
  
  public static void setStandardColor(Component com, int type, int degree) {
    if ((type < 0) || (type >= 7) || (degree < 0) || (degree >= 4))
      return;
    standardColorComponents[type][degree].addElement(com);
    com.setBackground(standardColors[type][degree]);
  }
  
  public static void standardHelpManager(String title)
  {
    Font helpFont = new Font(defaultFontFamily, 0, defaultFontSize);
    hm = new Jeli.Widgets.HelpManager(title, helpFont, null);
    hm.setHighlightColor(Color.cyan);
  }
  
  public static void addLinkChangeNotifier(Jeli.Widgets.LinkChangeNotifier lcn) {
    if (linkChangeList == null)
      linkChangeList = new Vector();
    linkChangeList.addElement(lcn);
  }
  
  public static void setLinksEnabled(boolean f) {
    linksEnabled = f;
    if (linkChangeList == null) return;
    for (int i = 0; i < linkChangeList.size(); i++)
      ((Jeli.Widgets.LinkChangeNotifier)linkChangeList.elementAt(i)).linkChangeNotify();
  }
  
  public static boolean getLinksEnabled() {
    return linksEnabled;
  }
  
  public static Vector newLink()
  {
    if (buttonLinks == null)
      buttonLinks = new Vector();
    Vector v = new Vector();
    buttonLinks.addElement(v);
    return v;
  }
  
  public static void addLink(Vector v, Jeli.Widgets.JeliButtonSimulator b) {
    if (b == null) return;
    v.addElement(b);
    b.setLinked(true);
  }
  
  public static void handleLink(Jeli.Widgets.JeliButtonSimulator b)
  {
    if (buttonLinks == null) return;
    if (!linksEnabled) return;
    for (int i = 0; i < buttonLinks.size(); i++) {
      Vector v = (Vector)buttonLinks.elementAt(i);
      for (int j = 0; j < v.size(); j++) {
        if (b == v.elementAt(j))
          handleLink(b, v);
      }
    }
  }
  
  private static void handleLink(Jeli.Widgets.JeliButtonSimulator b, Vector v) {
    for (int i = 0; i < v.size(); i++) {
      Jeli.Widgets.JeliButtonSimulator b1 = (Jeli.Widgets.JeliButtonSimulator)v.elementAt(i);
      if (b1 != b) b1.simulatePushed();
    }
  }
  
  public static void setHelpManager(Jeli.Widgets.HelpManager thm) {
    hm = thm;
  }
  
  public static String int2String(int n, int m)
  {
    String str = "" + n;
    while (str.length() < m)
      str = " " + str;
    return str;
  }
  
  public static String int2ZeroString(int n, int m)
  {
    String str = "" + n;
    while (str.length() < m)
      str = "0" + str;
    return str;
  }
  
  public static String n_places_0(double val, int n)
  {
    String s = n_places(val, n, 10);
    if (s.charAt(0) != '.')
      return s;
    return "0" + s;
  }
  
  public static String n_places(double val, int n) {
    return n_places(val, n, 10);
  }
  
  public static String n_places_right(double val, int n, int m)
  {
    String str = n_places(val, n);
    while (str.length() < m) str = " " + str;
    return str;
  }
  
  public static String n_places_right_or_blank_if_neg(double val, int n, int m) {
    String str;
    String str;
    if (val == 0.0D) {
      str = ""; } else { String str;
      if (val < 0.0D) {
        str = "-";
      } else
        str = n_places(val, n); }
    while (str.length() < m) str = " " + str;
    return str;
  }
  








  public static String n_places(double val, int n, int b)
  {
    double rounder = 0.5D;
    if (val == 0.0D) {
      if (n == 0) return "0";
      String str = "0.0";
      for (int i = 1; i < n; i++)
        str = str + "0";
      return str;
    }
    String sign = "";
    String str = sign;
    double val1 = val;
    for (int i = 0; i < n; i++)
      rounder /= b;
    if (val < 0.0D) {
      val1 = -val;
      sign = "-";
    }
    val1 += rounder;
    double val0 = val1;
    while (val1 >= 1.0D) {
      double val2 = (int)(val1 / b);
      
      str = basestring(val1 - b * val2) + str;
      
      val1 /= b;
    }
    val1 = val0 - (int)val0;
    
    if (n == 0) return sign + str;
    str = str + ".";
    for (int i = 0; i < n; i++) {
      val1 *= b;
      str = str + basestring(val1);
      val1 -= (int)val1;
    }
    









    return sign + str;
  }
  

  private static String basestring(double val)
  {
    return "" + (int)val;
  }
  
  public static int parseInt(java.util.StringTokenizer strtok)
    throws NumberFormatException
  {
    String str = strtok.nextToken();
    return Integer.parseInt(str);
  }
  

  public static double parseDouble(java.util.StringTokenizer strtok)
    throws NumberFormatException
  {
    String str = strtok.nextToken();
    double val = Double.valueOf(str).doubleValue();
    return val;
  }
  
  public static Point getRelativePosition(Component com) {
    return com.getLocation();
  }
  




  public static Point getRelativePosition(Component com, int n)
  {
    Point pos = com.getLocation();
    int x = x;
    int y = y;
    Component parent = com.getParent();
    while ((parent != null) && (n > 0)) {
      pos = parent.getLocation();
      x += x;
      y += y;
      parent = parent.getParent();
      n--;
    }
    return new Point(x, y);
  }
  
  public static Component getTopLevel(Component com)
  {
    Component parent = com.getParent();
    while (parent.getParent() != null)
      parent = parent.getParent();
    return parent;
  }
  
  public static Point getAbsoluteCenterPosition(Component com)
  {
    Point pos = getAbsolutePosition(com);
    x += getBoundswidth / 2;
    y += getBoundsheight / 2;
    return pos;
  }
  




  public static Point getAbsolutePosition(Component com)
  {
    Point pos = com.getLocation();
    int x = x;
    int y = y;
    Component parent = com.getParent();
    while (parent != null) {
      pos = parent.getLocation();
      x += x;
      y += y;
      parent = parent.getParent();
    }
    return new Point(x, y);
  }
  




  public static Point getRelativeTopLevelPosition(Component com)
  {
    Point pos = com.getLocation();
    int x = x;
    int y = y;
    Component parent = com.getParent();
    while ((parent != null) && (parent.getParent() != null)) {
      pos = parent.getLocation();
      x += x;
      y += y;
      parent = parent.getParent();
    }
    return new Point(x, y);
  }
  


  public static String getQuotedString(java.util.StringTokenizer stk)
  {
    String tok = stk.nextToken();
    if (tok.charAt(0) != '"') return tok;
    tok = tok.substring(1);
    if (tok.charAt(tok.length() - 1) == '"')
      return tok.substring(0, tok.length() - 1);
    tok = tok + stk.nextToken("\"");
    String test = stk.nextToken(" \n");
    return tok;
  }
  



  public static String removeExtraBlanks(String str)
  {
    StringBuffer buf = new StringBuffer();
    char lastchar = ' ';
    for (int i = 0; i < str.length(); i++) {
      char thischar = str.charAt(i);
      if ((thischar != ' ') || (lastchar != ' ')) {
        buf.append(thischar);
        lastchar = thischar;
      }
    }
    return buf.toString();
  }
  
  public static void show(Component com) {
    if (com != null) {
      com.setVisible(true);
      setVisibleComponent(com, true);
    }
  }
  
  public static void hide(Component com) {
    if (com != null) {
      com.setVisible(false);
      setVisibleComponent(com, false);
    }
  }
  
  public static int getWidth(Component com) {
    return getBoundswidth;
  }
  
  public static int getHeight(Component com) {
    return getBoundsheight;
  }
  
  public static java.awt.Insets insets(java.awt.Container cont) {
    return cont.getInsets();
  }
  
  public static void move(Component com, int x, int y) {
    com.setLocation(x, y);
  }
  
  public static void appendString(java.awt.TextArea ta, String str) {
    ta.append(str);
  }
  








  public static String[] formatArray(String[] str, int width, FontMetrics fm)
  {
    Vector V = new Vector();
    


    String c_str = "";
    int line = 0;
    int pos = 0;
    do {
      for (;;) { Point token_pos = find_next_token(str[line], pos);
        
        if (x < 0) break;
        String n_str; String n_str; if (c_str.endsWith(".")) {
          n_str = c_str + "  " + str[line].substring(x, y);
        } else
          n_str = c_str + " " + str[line].substring(x, y);
        int len = fm.stringWidth(n_str);
        
        if (len < width) {
          c_str = n_str;
          pos = y;


        }
        else if (c_str.length() == 0)
        {
          V.addElement(n_str);
          pos = y;
        }
        else {
          V.addElement(c_str);
          c_str = "";
        }
      }
      


      if (str[line].length() == 0) {
        if (c_str.length() != 0) {
          V.addElement(c_str);
          c_str = "";
        }
        V.addElement("");
      }
      line++;
      pos = 0;
    } while (line < str.length);
    if (c_str.length() > 0) { V.addElement(c_str);
    }
    


    return vector_to_string_array(V);
  }
  



  private static Point find_next_token(String s, int pos)
  {
    Point t_pos = new Point(-1, 0);
    

    int len = s.length();
    for (int p = pos; p < len; p++)
      if (s.charAt(p) != ' ') break;
    if (p >= len) return t_pos;
    x = p;
    for (p = x; p < len; p++)
      if (s.charAt(p) == ' ') break;
    y = p;
    return t_pos;
  }
  

  private static String[] vector_to_string_array(Vector v)
  {
    int len = v.size();
    
    String[] strs = new String[len];
    for (int i = 0; i < len; i++)
      strs[i] = new String((String)v.elementAt(i));
    return strs;
  }
  
  public static void drawCenteredString(int x, int y, String str, Graphics g)
  {
    g.drawString(str, start_to_string_center(x, str, g), y);
  }
  
  private static int start_to_string_center(int x, String str, Graphics g)
  {
    FontMetrics fm = g.getFontMetrics(g.getFont());
    return x - fm.stringWidth(str) / 2;
  }
  


  public static void drawStringRightJust(String str, int x, int y, Graphics g)
  {
    FontMetrics fm = g.getFontMetrics(g.getFont());
    int width = fm.stringWidth(str);
    g.drawString(str, x - width, y);
  }
  


  public static void sortStringArray(String[] str)
  {
    int size = str.length;
    for (int i = 0; i < size - 1; i++) {
      for (int j = 0; j < size - i - 1; j++) {
        if (str[j].compareTo(str[(j + 1)]) > 0) {
          String temp = str[j];
          str[j] = str[(j + 1)];
          str[(j + 1)] = temp;
        }
      }
    }
  }
  
  public static long createCheckSum(String s)
  {
    long sum = 0L;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      sum = sum + c * c * c * c * c - c + 1L;
    }
    return sum;
  }
  
  public static String createCheckSumString(String s) {
    return "" + createCheckSum(s);
  }
  
  public static String localTimeString()
  {
    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("EEE, MMM d, yyyy 'at' hh:mm:ss z");
    return df.format(new java.util.Date());
  }
  
  public static String localTimeString(long tm)
  {
    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("EEE, MMM d, yyyy 'at' hh:mm:ss z");
    return df.format(new java.util.Date(tm));
  }
  




  public static Font findFontGivenWidth(int n)
  {
    int pointsize = 10;
    Panel p = new Panel();
    Font f = new Font("MonoSpaced", 0, pointsize);
    int wid = p.getFontMetrics(f).stringWidth(" ");
    
    if (wid == n) return f;
    if (wid < n) {
      while ((wid < n) && (pointsize < 2 * n)) {
        pointsize++;
        f = new Font("MonoSpaced", 0, pointsize);
        wid = p.getFontMetrics(f).stringWidth(" ");
      }
      
      return f;
    }
    while ((wid > n) && (pointsize > 0)) {
      pointsize--;
      f = new Font("MonoSpaced", 0, pointsize);
      wid = p.getFontMetrics(f).stringWidth(" ");
    }
    
    return f;
  }
  




  public static Font findFontGivenHeight(int n)
  {
    int pointsize = 10;
    String family = defaultFontFamily;
    Panel p = new Panel();
    Font f = new Font(family, 0, pointsize);
    FontMetrics fm = p.getFontMetrics(f);
    int h = fm.getMaxAscent() + fm.getMaxDescent();
    
    if (h == n) return f;
    if (h < n) {
      while ((h < n) && (pointsize < 2 * n)) {
        pointsize++;
        f = new Font(family, 0, pointsize);
        fm = p.getFontMetrics(f);
        h = fm.getMaxAscent() + fm.getMaxDescent();
      }
      
      return f;
    }
    while ((h > n) && (pointsize > 0)) {
      pointsize--;
      f = new Font(family, 0, pointsize);
      fm = p.getFontMetrics(f);
      h = fm.getMaxAscent() + fm.getMaxDescent();
    }
    
    return f;
  }
  


  public static java.awt.Polygon getLineArrow(int x1, int y1, int x2, int y2, int arrowWidth, int arrowLength)
  {
    int sign = 1;
    




    int[] xs = new int[4];
    int[] ys = new int[4];
    xs[0] = x2;
    ys[0] = y2;
    xs[3] = x2;
    ys[3] = y2;
    if (y2 == y1) {
      if (x1 < x2) sign = -1;
      xs[1] = (x2 + sign * arrowLength);
      xs[2] = xs[1];
      ys[1] = (y2 - arrowWidth);
      ys[2] = (y2 + arrowWidth);
    }
    else if (x2 == x1) {
      if (y1 < y2) sign = -1;
      xs[1] = (x2 - arrowWidth);
      xs[2] = (x2 + arrowWidth);
      ys[1] = (y2 + sign * arrowLength);
      ys[2] = ys[1];
    }
    else {
      double slope = (x2 - x1) / (y1 - y2);
      double sqrtmp1 = Math.sqrt(1.0D + slope * slope);
      double length = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
      double xa = x2 - arrowLength * ((x2 - x1) / length);
      double ya = y2 - arrowLength * ((y2 - y1) / length);
      xs[1] = ((int)(xa + arrowWidth / sqrtmp1 + 0.5D));
      ys[1] = ((int)(ya + arrowWidth * slope / sqrtmp1 + 0.5D));
      xs[2] = ((int)(xa - arrowWidth / sqrtmp1 + 0.5D));
      ys[2] = ((int)(ya - arrowWidth * slope / sqrtmp1 + 0.5D));
    }
    return new java.awt.Polygon(xs, ys, 4);
  }
  

  public static Point circlePoint(int x, int y, int x1, int y1, int radius)
  {
    double d = Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    int x2 = x + (int)(radius / d * (x1 - x) + 0.5D);
    int y2 = y + (int)(radius / d * (y1 - y) + 0.5D);
    return new Point(x2, y2);
  }
  
  public static void setLogger(Jeli.Logging.StandardLogger lgr) {
    logger = lgr;
  }
  
  public static Jeli.Logging.StandardLogger getLogger() {
    return logger;
  }
  
  public static void registerStandardLogButtons(Jeli.Logging.StandardLogButtons slbs) {
    slb = slbs;
  }
  
  public static Jeli.Logging.StandardLogButtons getStandardLogButtons() {
    return slb;
  }
  
  public static void unregisterLogButton(Component com) {
    if (slb != null) {
      slb.unregisterLogButton(com);
    }
  }
  
  public static void buttonPushed(JeliButton bh, boolean pushed)
  {
    if (pushedButtonLinks == null)
      return;
    for (int i = 0; i < pushedButtonLinks.size(); i++) {
      Vector thisList = (Vector)pushedButtonLinks.elementAt(i);
      for (int j = 0; j < thisList.size(); j++) {
        if (thisList.elementAt(j) == bh) {
          for (int k = 0; k < thisList.size(); k++) {
            JeliButton lb = (JeliButton)thisList.elementAt(k);
            if (lb != bh)
              lb.simulatePushed(pushed);
          }
        }
      }
    }
  }
  
  public static void addPushedButtonList(Vector v) {
    if (pushedButtonLinks == null)
      pushedButtonLinks = new Vector();
    pushedButtonLinks.addElement(v);
    for (int i = 0; i < v.size(); i++)
      ((JeliButton)v.elementAt(i)).setPushedLinked(true);
  }
  
  public static void addPushedButtonPair(JeliButton b1, JeliButton b2) {
    Vector v = new Vector();
    v.addElement(b1);
    v.addElement(b2);
    addPushedButtonList(v);
    b1.setPushedLinked(true);
    b2.setPushedLinked(true);
  }
  
  public static void printStackTrace(String s) {
    System.out.println(s);
    try {
      Integer.parseInt("1b");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static void setVisibleComponent(Component com, boolean f) {
    if (visibleComponentList == null)
      visibleComponentList = new Vector();
    if (com == null) {
      return;
    }
    if (f) {
      for (int i = 0; i < visibleComponentList.size(); i++)
        if (visibleComponentList.elementAt(i) == com)
          return;
      visibleComponentList.addElement(com);
    }
    else {
      visibleComponentList.removeElement(com);
    }
  }
  


  public static void setAllInvisible()
  {
    if (visibleComponentList == null)
      return;
    int size = visibleComponentList.size();
    if (size == 0)
      return;
    Component[] coms = new Component[size];
    for (int i = 0; i < size; i++)
      coms[i] = ((Component)visibleComponentList.elementAt(i));
    for (int i = 0; i < size; i++) {
      if ((coms[i] instanceof Jeli.Get.ForceKeepFront)) {
        ((Jeli.Get.ForceKeepFront)coms[i]).forceHide();
      } else
        coms[i].setVisible(false);
    }
    visibleComponentList.removeAllElements();
  }
  
  public static Vector getVisibleComponentList() {
    return visibleComponentList;
  }
  
  public static java.awt.Dimension getScreenSize()
  {
    java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
    if (tk == null)
      return new java.awt.Dimension(640, 480);
    return tk.getScreenSize();
  }
  
  public static void standardScale(Jeli.Widgets.JeliFrame f)
  {
    java.awt.Dimension sz = getScreenSize();
    f.scale(width / 2, 2 * height / 3);
  }
  




  public static int[] sortDouble(double[] vals)
  {
    int size = vals.length;
    int[] indexes = new int[size];
    for (int i = 0; i < size; i++)
      indexes[i] = i;
    for (int i = 0; i < size - 1; i++) {
      for (int j = 0; j < size - i - 1; j++)
        if (vals[indexes[j]] > vals[indexes[(j + 1)]]) {
          int temp = indexes[j];
          indexes[j] = indexes[(j + 1)];
          indexes[(j + 1)] = temp;
        }
    }
    return indexes;
  }
  
  public static String getJavaVersion() {
    return "Java version " + System.getProperty("java.version") + " OS " + System.getProperty("os.name") + " version " + System.getProperty("os.version");
  }
  

  public static String getJavaOnlyVersion()
  {
    return "Java version " + System.getProperty("java.version");
  }
  
  public static String getOSVersion() {
    return "OS " + System.getProperty("os.name") + " version " + System.getProperty("os.version");
  }
  
  public static void setInfoFrame(Jeli.Widgets.JeliTextFrame info)
  {
    infoFrame = info;
  }
  
  public static void setInfoVerbose(boolean f) {
    infoVerbose = f;
  }
  
  public static boolean getInfoVerbose() {
    return infoVerbose;
  }
  
  public static void appendInfo(String s) {
    if (infoFrame == null)
      return;
    infoFrame.append(s);
  }
  
  public static void appendInfoTime(String s) {
    appendInfo(localTimeString() + ": " + s);
  }
  
  public static void appendInfoTime(String s, long tm) {
    appendInfo(localTimeString(tm) + ": " + s);
  }
  
  public static void appendInfoVerbose(String s) {
    if (infoVerbose)
      appendInfo(s);
  }
  
  public static void appendInfoTimeVerbose(String s) {
    if (infoVerbose)
      appendInfoTime(s);
  }
  
  public static void appendInfoTimeVerbose(String s, long tm) {
    if (infoVerbose) {
      appendInfoTime(s, tm);
    }
  }
  
  public static boolean startsWithIgnoreCase(String s1, String s2) {
    if (s1.length() < s2.length())
      return false;
    for (int i = 0; i < s2.length(); i++)
      if (Character.toLowerCase(s1.charAt(i)) != Character.toLowerCase(s2.charAt(i)))
      {
        return false; }
    return true;
  }
  
  public static void setRuns(String[] runs) {
    allruns = runs;
  }
  
  public static void setExps(String[] exps) {
    allexps = exps;
  }
  
  public static String getRunFromName(String s) {
    if (allruns == null)
      return null;
    for (int i = 0; i < allruns.length; i++)
      if (checkName(allruns[i], s))
        return allruns[i];
    return null;
  }
  
  public static String getExpFromName(String s) {
    if (allexps == null)
      return null;
    for (int i = 0; i < allexps.length; i++)
      if (checkName(allexps[i], s))
        return allexps[i];
    return null;
  }
  


  public static boolean checkName(String s, String t)
  {
    String s1 = s.trim();
    int ind = s1.indexOf('\n');
    if (ind == -1)
      return false;
    s1 = s1.substring(0, ind);
    if (!startsWithIgnoreCase(s, "name "))
      return false;
    s1 = s1.substring(5).trim();
    return s1.equals(t);
  }
  
  public static void addInternalFile(String name, String data)
  {
    if (data == null) return;
    String[] file = new String[2];
    file[0] = name;
    file[1] = data;
    if (internalFiles == null)
      internalFiles = new Vector();
    internalFiles.addElement(file);
  }
  
  public static String getInternalFile(String name)
  {
    if (internalFiles == null)
      return null;
    for (int i = 0; i < internalFiles.size(); i++) {
      String[] file = (String[])internalFiles.elementAt(i);
      if (name.equals(file[0]))
        return file[1];
    }
    return null;
  }
  
  public static void resetNextLocation(JeliBounds f) {
    lastFrameDisplayed = f;
    lastFrameDisplayedTop = f;
  }
  


  public static Point getNextLocation(JeliBounds f)
  {
    if (lastFrameDisplayed == null) {
      lastFrameDisplayed = f;
      lastFrameDisplayedTop = f;
      return new Point(0, 0);
    }
    java.awt.Rectangle bounds = lastFrameDisplayed.getBounds();
    java.awt.Rectangle fBounds = f.getBounds();
    Point p = new Point(x, y + height);
    lastFrameDisplayed = f;
    if (y + height < getScreenSizeheight) {
      return p;
    }
    if (lastFrameDisplayedTop == null) {
      lastFrameDisplayedTop = f;
      return new Point(0, 0);
    }
    bounds = lastFrameDisplayedTop.getBounds();
    x = width;
    y = 0;
    lastFrameDisplayedTop = f;
    return p;
  }
  
  public static Jeli.Logging.EmailSender createEmailSender(String to, String from, String host)
  {
    try {
      return new Jeli.Logging.EmailSender(to, from, host);
    }
    catch (Throwable t) {}
    return null;
  }
  


  public static boolean setupEmailSender(String to, String from, String host)
  {
    es = createEmailSender(to, from, host);
    if (es == null)
      return false;
    return true;
  }
  
  public static boolean sendHtmlMessage(String s, String sub) {
    if (es == null)
      return false;
    return es.sendHtmlMessage(s, sub);
  }
  
  public static boolean sendTextMessage(String s, String sub) {
    if (es == null)
      return false;
    return es.sendTextMessage(s, sub);
  }
  
  public static void garbageCollect()
  {
    Runtime rt = Runtime.getRuntime();
    if (rt == null)
      return;
    rt.gc();
  }
  

  public static String[] jeliArguments(String[] args)
  {
    boolean jArgsFound = false;
    if ((args == null) || (args.length == 0))
      return args;
    Vector argsVector = new Vector();
    for (int i = 0; i < args.length; i++) {
      if ((args[i].equals("-jfontsize")) && (i < args.length - 1)) {
        i++;
        try {
          defaultFontSize = Integer.parseInt(args[i]);
          jArgsFound = true;
        }
        catch (NumberFormatException e) {}
      }
      else if ((args[i].equals("-jfontfamily")) && (i < args.length - 1)) {
        i++;
        defaultFontFamily = args[i];
        jArgsFound = true;
      }
      else if (args[i].equals("-jversion")) {
        System.out.println(JeliVersion.fullVersionMessage());
        System.out.println(appVersionString);
        System.exit(0);
      }
      else if (args[i].equals("-jdebugio")) {
        UtilityIO.setDebugIO(true);
        jArgsFound = true;
      }
      else if ((args[i].equals("-jbrowser")) && (i < args.length - 1)) {
        i++;
        UtilityIO.setBrowser(args[i]);
        jArgsFound = true;
      }
      else {
        argsVector.addElement(args[i]);
      }
    }
    if (jArgsFound) {
      String[] newArgs = new String[argsVector.size()];
      for (int i = 0; i < argsVector.size(); i++)
        newArgs[i] = ((String)argsVector.elementAt(i));
      return newArgs;
    }
    return args;
  }
  
  public static void jeliAppletParameters(java.applet.Applet app)
  {
    String s = app.getParameter("JFONTSIZE");
    if (s != null) {
      try
      {
        defaultFontSize = Integer.parseInt(s);
      }
      catch (NumberFormatException e) {}
    }
    s = app.getParameter("JFONTFAMILY");
    if (s != null)
      defaultFontFamily = s;
    s = app.getParameter("JVERSION");
    if (s != null) {
      System.out.println(JeliVersion.fullVersionMessage());
      System.out.println(appVersionString);
    }
    s = app.getParameter("JDEBUGIO");
    if (s != null)
      UtilityIO.setDebugIO(true);
    setBackURL(app.getParameter("BACKURL"));
    s = app.getParameter("REMOTEPORT");
    
    if (s != null) {
      try {
        remotePort = Integer.parseInt(s);
      }
      catch (NumberFormatException e) {}
    }
  }
  


  public static Panel makeAppletPanel(Jeli.Widgets.JeliButtonCallBack cb, JeliApplicationVersion version)
  {
    makeAppletButtons(cb, jeliLightGreen, version);
    Panel p = new Panel();
    p.setLayout(new Jeli.Widgets.JeliGridLayout(2, 1));
    p.add(getStartButton());
    p.add(getInfoButton());
    return p;
  }
  
  public static Panel makeAppletLabel(JeliApplicationVersion version)
  {
    makeAppletButtons(null, Color.white, version);
    Panel p = new Panel();
    p.setLayout(new Jeli.Widgets.JeliGridLayout(2, 1));
    p.add(getStartButton());
    p.add(getInfoButton());
    return p;
  }
  
  public static void setStartButtonNameStart() {
    startButton.setLabel(" Start " + applicationName);
  }
  
  public static void setStartButtonNameShow() {
    startButton.setLabel("  Show " + applicationName + "  ");
  }
  
  private static void makeAppletButtons(Jeli.Widgets.JeliButtonCallBack cb, Color bColor, JeliApplicationVersion version)
  {
    String initString = "  Start ";
    boolean badVersion = false;
    if ((version != null) && (version.getMinimalJeliVersion() > 288))
    {
      badVersion = true; }
    if (badVersion) {
      startButton = new JeliButton(applicationName, hm, cb);
      startButton.setFontSize(12);
      startButton.setAsLabel();
      startButton.setButtonHeight(2);
      startButton.setLabelArray(applicationName + "\nRequires Jeli Version " + version.getMinimalJeliVersion());
    }
    else
    {
      if (cb == null)
        initString = "  ";
      startButton = new JeliButton(initString + applicationName + "  ", hm, cb);
      
      startButton.setFontSize(18);
      startButton.setButtonSize();
    }
    startButton.setBackground(bColor);
    startButton.setPositionCenter();
    startButton.setBorderType(13);
    infoButton = new JeliButton("infoButton", hm, cb);
    infoButton.setFontSize(10);
    infoButton.setButtonHeight(3);
    infoButton.setLabelArray("Version " + applicationVersion + "\n" + applicationUpdate + "\n ");
    
    infoButton.setLabelArray2(" \n \nJELI Version 288, updated March 1, 2007");
    infoButton.setLabelArray3(getJavaOnlyVersion() + "\n" + getOSVersion() + "\n ");
    infoButton.setBackground(bColor);
    infoButton.setBorderType(14);
    if (badVersion)
      infoButton.setAsLabel();
    if (cb == null) {
      startButton.setAsLabel();
      infoButton.setAsLabel();
      startButton.setBorderType(0);
      infoButton.setBorderType(0);
    }
    addPushedButtonPair(startButton, infoButton);
  }
  
  public static void setApplicationName(String s) {
    applicationName = s;
  }
  
  public static String getApplicationName() {
    return applicationName;
  }
  
  public static void setApplicationVersion(String s) {
    applicationVersion = s;
  }
  
  public static String getApplicationVersion() {
    return applicationVersion;
  }
  
  public static void setApplicationUpdate(String s) {
    applicationUpdate = s;
  }
  
  public static JeliButton getStartButton() {
    return startButton;
  }
  
  public static JeliButton getInfoButton() {
    return infoButton;
  }
  
  public static boolean makeUndecorated(Object f) {
    try {
      if ((f instanceof java.awt.Frame)) {
        ((java.awt.Frame)f).setUndecorated(true);
      } else if ((f instanceof java.awt.Dialog)) {
        ((java.awt.Dialog)f).setUndecorated(true);
      } else
        return false;
      return true;
    }
    catch (NoSuchMethodError e) {}
    return false;
  }
  
  public static void setBackURL(String s)
  {
    if (s == null)
      return;
    try {
      backURL = new java.net.URL(s);
    }
    catch (java.net.MalformedURLException e) {
      backURL = null;
    }
  }
  
  public static void goToBackURL(Component com) {
    java.applet.AppletContext app = UtilityIO.getAppletContext();
    if (app != null) {
      if (com != null)
        com.setVisible(false);
      setAllInvisible();
    }
    if ((app != null) && (backURL != null))
      app.showDocument(backURL);
  }
  
  public static int getRemotePort() {
    return remotePort;
  }
  
  public static void setRemotePort(int n) {
    remotePort = n;
  }
  
  public static void checkMinimalVersion(JeliApplicationVersion version) {
    if (version.getMinimalJeliVersion() > 288) {
      System.out.println("This program requires a later version of Jeli than the one provided.");
      
      System.out.println(getAppVersionString());
      System.exit(0);
    }
  }
  
  protected static void setVersionMessage(JeliApplicationVersion version) {
    String fullVersionMessage = version.getName() + " version " + version.getVersionMessageShort() + " last updated " + version.getVersionMessageUpdate() + "\nUsing Jeli " + 288 + " last updated " + "March 1, 2007" + "\nRequires Jeli verison " + version.getMinimalJeliVersion() + " or later.";
    





    setAppVersionString(fullVersionMessage);
    appVersion = version;
  }
  
  public static JeliApplicationVersion getAppVersion() {
    return appVersion;
  }
  
  public static String[] setupApplication(JeliApplicationVersion version, String[] args)
  {
    setVersionMessage(version);
    args = jeliArguments(args);
    standardHelpManager(version.getName());
    checkMinimalVersion(version);
    return args;
  }
  
  public static Panel getBorderedPanel(Component com) {
    Panel p = new Jeli.Widgets.JeliInsetsPanel(1, Color.black);
    p.setLayout(new java.awt.GridLayout(1, 1));
    p.add(com);
    return p;
  }
  


  public static String stringToHTML(String str)
  {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      if (ch == '\n')
        buf.append("<br>");
      buf.append(ch);
    }
    return buf.toString();
  }
  
  public static void drawArrow(Graphics g, Point p1, Point p2) {
    if ((p1 == null) || (p2 == null))
      return;
    drawArrow(g, x, y, x, y);
  }
  
  public static void drawArrow(Graphics g, int x1, int y1, int x2, int y2)
  {
    double d = 7.0D;
    double foot = 5.0D;
    






    int[] xs = new int[3];
    int[] ys = new int[3];
    g.fillOval(x1 - 2, y1 - 2, 5, 5);
    double len = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    
    double x3 = x2 - (x2 - x1) * d / len;
    double y3 = y2 - (y2 - y1) * d / len;
    double xDiff = (y2 - y1) * foot / len;
    double yDiff = (x1 - x2) * foot / len;
    double x4 = x3 + xDiff;
    double y4 = y3 + yDiff;
    double x5 = x3 - xDiff;
    double y5 = y3 - yDiff;
    xs[0] = x2;
    xs[1] = ((int)x4);
    xs[2] = ((int)x5);
    ys[0] = y2;
    ys[1] = ((int)y4);
    ys[2] = ((int)y5);
    g.fillPolygon(xs, ys, 3);
    g.drawLine(x1, y1, x2, y2);
  }
  
  public static void setMessenger(JeliMessenger m)
  {
    messenger = m;
  }
  
  public static void displayMessage(int type, String s) {
    if (messenger == null) {
      if (s.endsWith("\n")) {
        System.out.print(s);
      } else {
        System.out.println(s);
      }
    } else
      messenger.displayMessage(type, s);
  }
  
  public static void setConfigurationNames(String s, String defaultfn) {
    if (s == null) {
      return;
    }
    try {
      java.io.File f = new java.io.File(s);
      
      if (f.isDirectory())
      {
        configSeparator = System.getProperty("file.separator");
        configFilename = defaultfn;
        configDirectory = s + configSeparator;
        return;
      }
    }
    catch (Exception e) {}
    

    setConfigurationNames(s);
    if (configFilename.length() == 0)
      configFilename = defaultfn;
  }
  
  public static String todaysDebugNumber() {
    String s = new java.util.Date().toString();
    long n = 0L;
    s = s.substring(0, 10) + s.substring(24);
    
    for (int i = 0; i < s.length(); i++) {
      n = n * 29L + s.charAt(i) - 32L;
    }
    s = "" + n;
    if (s.length() > 7) {
      s = s.substring(s.length() - 7);
    }
    return s;
  }
  
  public static void setConfigurationNames(String s)
  {
    if (s == null)
      return;
    int ind = s.lastIndexOf("/");
    if (ind != -1) {
      configSeparator = "/";
    } else {
      ind = s.lastIndexOf("\\");
      if (ind != -1) {
        configSeparator = "\\";
      } else
        configSeparator = System.getProperty("file.separator");
    }
    if (ind == -1) {
      configFilename = s;
      configDirectory = "";
    }
    else {
      configFilename = s.substring(ind + 1);
      configDirectory = s.substring(0, ind + 1);
    }
  }
}
