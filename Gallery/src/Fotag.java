

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.nio.file.attribute.*;
import java.util.concurrent.TimeUnit;


public class Fotag {
    public static void main(String args[]) {
        System.out.println("Start!");
        new MainFrame();
    }
}

class PicDescripPanel extends JPanel{
    String pic_name_raw;
    String date;
    int rate;
    StarRating SR;
    PicDescripPanel(String pic_name_raw, int r){
        this.pic_name_raw = pic_name_raw;
        this.rate = r;
        String file_name = "./src/imgs/" + pic_name_raw;
        File file = new File(file_name);
        Path filePath = file.toPath();
        BasicFileAttributes attributes = null;
        try
        {
            attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
        }
        catch (IOException exception) {
            System.out.println("Exception handled when trying to get file " +
                    "attributes: " + exception.getMessage());
        }

        Date creationDate = new Date(attributes.creationTime().to(TimeUnit.MILLISECONDS));

        date = creationDate.getDate() + "/" +
                (creationDate.getMonth() + 1) + "/" +
                (creationDate.getYear() + 1900);

        String pic_name = pic_name_raw;
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setPreferredSize(new Dimension(300, 40));
        //add(new JLabel(pic_name), BorderLayout.AFTER_LINE_ENDS);

        JPanel b = new JPanel();
        b.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));
        SR = new StarRating(this);
        b.add(new JLabel(date));
        b.add(SR);
        b.setBackground(Color.white);
        b.setPreferredSize(new Dimension(300, 50));
        add(b, BorderLayout.CENTER);
        // add(new StarRating(3), BorderLayout.SOUTH);
    }
    void setrate(int r){
        rate = r;
    }
}
class FilterStarMouse implements MouseInputListener{
    FilterStar  s;
    int r;
    MainPanel mp;
    public FilterStarMouse(FilterStar s, int r, MainPanel p) {
        this.s = s;
        this.r = r;
        this.mp = p;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       // System.out.println(s.index);
        if(s.is_on){
           // ppd.setrate(s.index) ;x
            //System.out.print("Filter by star: ");
            //System.out.println(s.index);
            s.Turn_off();
            mp.filter_rate = s.index;
            mp.filter();
        }
        else{
            //ppd.setrate(s.index+1);
            //System.out.print("Filter by star: ");
            //System.out.println(s.index+1);
            s.Turn_on();
            mp.filter_rate = s.index+1;
            mp.filter();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
class StarMouse implements MouseInputListener{
    Star  s;
    int r;
     PicDescripPanel ppd;
    public StarMouse(Star s, PicDescripPanel pd) {
        this.s = s;
        this.r = pd.rate;
        ppd = pd;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println(s.index);
        if(s.is_on){
            ppd.setrate(s.index);
            //System.out.print("Filter by star: ");
            //System.out.println(s.index);
            s.Turn_off();
        }
        else{
            ppd.setrate(s.index+1);
            //System.out.print("Filter by star: ");
            //System.out.println(s.index//+1);
            s.Turn_on();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
class Star extends JLabel {

    //Star Icons made by Smashicons frSom flaticon.com
    boolean is_on;
    String off_path = "./src/staroff.png";
    String on_path = "./src/staron.png";
    int index;
    ImageIcon star_on;
    ImageIcon star_off;
    ImageIcon cur;
    List<Star> l;
    int rate;

    //private int index;
    Star(boolean c, int i, List<Star> l, PicDescripPanel pd){

        //setMaximumSize(new Dimension(5, 5));
        // Image img = ImageIO.read(new File("./src/imgs/bunny.jpg")).getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        index = i;
        is_on = c;
        this.l = l;
        this.rate = pd.rate;
        try{
            star_on = new ImageIcon(ImageIO.read(new File(on_path)).getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            star_off = new ImageIcon(ImageIO.read(new File(off_path)).getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        } catch(Exception e){
            System.out.println(e);
        }
        if(is_on){
            cur = star_on;
            setIcon(cur);
        }
        else{
            cur = star_off;
            setIcon(cur);
        }
    }

    void Turn_off(){
        cur = star_off;
        setIcon(cur);
        is_on = false;

        for(int i = index + 1; i < 5; i++){
            l.get(i).cur = l.get(i).star_off;
            l.get(i).setIcon(l.get(i).cur);
            l.get(i).is_on = false;
            rate = index;
        }

        revalidate();
        repaint();
    }
    void Turn_on(){
        cur = star_on;
        setIcon(cur);
        is_on = true;
        for(int i = index ; i >= 0; i--){
            l.get(i).cur = l.get(i).star_on;
            l.get(i).setIcon(l.get(i).cur);
            l.get(i).is_on = true;
        }
        revalidate();
        repaint();
    }
}

class FilterStar extends JLabel {

    //Star Icons made by Smashicons frSom flaticon.com
    boolean is_on;
    String off_path = "./src/staroff.png";
    String on_path = "./src/staron.png";
    int index;
    ImageIcon star_on;
    ImageIcon star_off;
    ImageIcon cur;
    List<FilterStar> l;
    int rate;

    //private int index;
    FilterStar(boolean c, int i, List<FilterStar> l, int r){

        //setMaximumSize(new Dimension(5, 5));
        // Image img = ImageIO.read(new File("./src/imgs/bunny.jpg")).getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        index = i;
        is_on = c;
        this.l = l;
        this.rate = r;
        try{
            star_on = new ImageIcon(ImageIO.read(new File(on_path)).getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            star_off = new ImageIcon(ImageIO.read(new File(off_path)).getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        } catch(Exception e){
            System.out.println(e);
        }
        if(is_on){
            cur = star_on;
            setIcon(cur);
        }
        else{
            cur = star_off;
            setIcon(cur);
        }
    }

    void Turn_off(){
        cur = star_off;
        setIcon(cur);
        is_on = false;

        for(int i = index + 1; i < 5; i++){
            l.get(i).cur = l.get(i).star_off;
            l.get(i).setIcon(l.get(i).cur);
            l.get(i).is_on = false;
            rate = index;
        }

        revalidate();
        repaint();
    }
    void Turn_on(){
        cur = star_on;
        setIcon(cur);
        is_on = true;
        for(int i = index ; i >= 0; i--){
            l.get(i).cur = l.get(i).star_on;
            l.get(i).setIcon(l.get(i).cur);
            l.get(i).is_on = true;
        }
        revalidate();
        repaint();
    }
}

class FilterStarRating extends JPanel{
    int rate;
    FilterStarRating(MainPanel mp){
        setBorder(BorderFactory.createLineBorder(Color.black));
        setSize(new Dimension(100, 300));
        rate = mp.filter_rate;
        setLayout(new FlowLayout());
        List<FilterStar> list_star = new ArrayList<FilterStar>();
        for(int i = 0; i < 5; i++){
            if(i <= rate-1){
                FilterStar s = new FilterStar(true, i, list_star, rate);
                s.addMouseListener(new FilterStarMouse(s, rate, mp));
                list_star.add(s);
            }
            else{
                FilterStar s = new FilterStar(false, i, list_star, rate);
                s.addMouseListener(new FilterStarMouse(s, rate, mp));
                list_star.add(s);
            }
            add(list_star.get(i));
        }

    }
}

class StarRating extends JPanel{
    int starrating_rate;
    StarRating(PicDescripPanel pd){
        setBorder(BorderFactory.createLineBorder(Color.black));
        setSize(new Dimension(100, 300));
        this.starrating_rate = pd.rate;
        setLayout(new FlowLayout());
        List<Star> list_star = new ArrayList<Star>();
        for(int i = 0; i < 5; i++){
            if(i <= starrating_rate-1){
                Star s = new Star(true, i, list_star, pd);
                s.addMouseListener(new StarMouse(s, pd));
                list_star.add(s);
            }
            else{
                Star s = new Star(false, i, list_star, pd);
                s.addMouseListener(new StarMouse(s, pd));
                list_star.add(s);
            }
            add(list_star.get(i));
        }

    }
}


class Pic_Frame extends JFrame{
    String path;
    Pic_Frame(String p, String pic_name, PicDescripPanel pd){
        super(pic_name);
        setMaximumSize(new Dimension(800, 600));
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(Pic_Frame.HIDE_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        path = p;
        JLabel l = new JLabel();
        try{
            //ImageIcon img = new ImageIcon(this.getClass().getResource(path));
            //l.setIcon(img);
            Image img = ImageIO.read(new File(p)).getScaledInstance(800, 600, Image.SCALE_DEFAULT);
            l.setIcon(new ImageIcon(img));

        } catch (Exception e){
            System.out.println(e);
        }
        add(l, BorderLayout.CENTER);

        add(new StarRating(pd), BorderLayout.SOUTH);
    }
}

class MyWindowA extends WindowAdapter {
    MainPanel m;
    MyWindowA(MainPanel mm){
        m = mm;
    }
    @Override
    public void windowClosing(WindowEvent e) {
        //System.out.println("sssss");
        //
        // System.out.println(m.pics.size());


        m.pic_txt.delete();
        try{
            PrintWriter writer = new PrintWriter(m.pic_txt, "UTF-8");
            writer.print("");
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }

        try{
            //here
            FileWriter fw = new FileWriter(m.pic_txt, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i < m.pics.size(); i++){
                //System.out.println(m.pics.get(i).pic_des_panel.rate);
                bw.write(m.pics.get(i).pic_name + "," + m.pics.get(i).pic_des_panel.rate);
                bw.newLine();
            }

            bw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

class MainFrame extends JFrame {

    Toolbar toolbar;
    static MainPanel mainpanel;
    static JScrollPane scroll_p;
    MainFrame(){

        super("Fotag!");
        setLayout(new BorderLayout());

        mainpanel = new MainPanel();

        scroll_p = new JScrollPane(mainpanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_p.getVerticalScrollBar().setUnitIncrement(15);
        toolbar = new Toolbar(mainpanel, scroll_p);
        add(toolbar, BorderLayout.NORTH);

        add(scroll_p);

        addWindowListener(new MyWindowA(mainpanel));

        setMinimumSize(new Dimension(600, 300));
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
}

class Grid_button_Listener implements MouseInputListener{
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Grid button");
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) { }
}
class List_button_Listener implements MouseInputListener{
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("List button");
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) { }
}
class Load_button_Listener implements MouseInputListener{
    static MainPanel m;
    static JScrollPane s;
    static JFileChooser c;
    public Load_button_Listener(MainPanel mainpanel, JScrollPane scrollPane, JFileChooser chooser) {
        m = mainpanel;
        s = scrollPane;
        c = chooser;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("Load button");
        int result = c.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            String name = c.getSelectedFile().getName();

            //System.out.println(name);
            m.pics.add(new PicPanel(name, 5));
            //System.out.println(m.pics.size());
            m.add(m.pics.get(m.pics.size()-1));
            if(m.pics.size() > 0){
                s.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
               // m.setPreferredSize(new Dimension(400,800));
            }
            try{
                FileWriter fw = new FileWriter(m.pic_txt, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(name + ",5");
                bw.newLine();
                bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            m.revalidate();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) { }
}
class Clear_button_Listener implements MouseInputListener{
    static MainPanel m;
    static JScrollPane s;
    public Clear_button_Listener(MainPanel mainpanel, JScrollPane scrollPane) {
        m = mainpanel;
        s = scrollPane;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        m.clear_pic();
       // m.setPreferredSize(new Dimension(400,800));
        s.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        //System.out.println(m.pics.size());
        m.pic_txt.delete();
        try{
            PrintWriter writer = new PrintWriter(m.pic_txt, "UTF-8");
            writer.print("");
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) { }
}

class Toolbar extends JToolBar{

    private JFileChooser chooser;
    private JButton grid_b;
    private JButton list_b;
    private JButton load_b;
    private JButton clear_b;
    private JLabel Fotag_label;
    FilterStarRating s_filter;


    Toolbar(MainPanel mainpanel, JScrollPane scrollPane){

        setLayout(new AlignLayout(40, 40));
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("./src/imgs"));
        grid_b = new JButton("Grid");
        grid_b.addMouseListener(new Grid_button_Listener());
        list_b = new JButton("List");
        list_b.addMouseListener(new List_button_Listener());
        load_b = new JButton("Load");
        load_b.addMouseListener(new Load_button_Listener(mainpanel, scrollPane, chooser));
        clear_b = new JButton("Clear");
        clear_b.addMouseListener(new Clear_button_Listener(mainpanel, scrollPane));
        Fotag_label = new JLabel("FOTAG!!");
        s_filter = new FilterStarRating(mainpanel);
        add(grid_b);
        add(list_b);
        add(Fotag_label);
        add(load_b);
        add(clear_b);
        add(new JLabel("Filter:"));
        add(s_filter);
        setFloatable(false);
    }
}

class MainPanel extends JPanel {
    public static String pic_addr = "./src/pic_info.txt";
    public static File pic_txt = new File(pic_addr);
    int filter_rate = 0;
    public static List<PicPanel> pics = new ArrayList();

    public void clear_pic(){
        pics.clear();
        removeAll();
        revalidate();
        repaint();
    }
    public void filter(){
        removeAll();
        for(int i = 0; i < pics.size(); i++){
            //System.out.println("picture rate:");
            //System.out.println(pics.get(i).pic_des_panel.rate);
            if(pics.get(i).pic_des_panel.rate >= filter_rate){
                add(pics.get(i));
            }
        }
        revalidate();
        repaint();
    }
    MainPanel() {
        setBackground(Color.white);

        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
       // setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400,5100));


        try{
            Scanner scanner = new Scanner(pic_txt);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.length() > 2){
                    String[] data = line.split(",", 2);
                    String pic_name = data[0];
                    int rate = Integer.parseInt(data[1]);
                    //System.out.println(pic_name);
                    pics.add(new PicPanel(pic_name, rate));
                    add(pics.get(pics.size()-1));
                }

                //d.put(pic_name, rate);
            }
        } catch (FileNotFoundException e){
            System.out.println("Check if imgs data file is at the right directory. ./src/pic_info.txt");
            e.printStackTrace();
        }
    }
}

class Picture{
    private String file_name;
    private int rate;
    private String last_date;

    Picture(String f, int r, String d){
        file_name = f;
        rate = r;
        last_date = d;
    }
    public String get_name(){
        return file_name;
    }
    public int get_rate(){
        return rate;
    }
    public String get_last_date(){
        return last_date;
    }
    public void set_rate(int r){
        rate = r;
    }
}

class PicPanel extends JPanel{
    PicButton pic;
    private String pic_source;
    String pic_name;
    private String date;
    int pic_rate;
    private JLabel name_label;
    PicDescripPanel pic_des_panel;

    PicPanel(String pic_name_a, int r){
        pic_name = pic_name_a;
        pic_des_panel = new PicDescripPanel(pic_name, this.pic_rate);
        pic = new PicButton(pic_name, pic_des_panel);
        pic_rate = r;
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setPreferredSize(new Dimension(300, 325));
        add(pic, BorderLayout.NORTH);
        pic_des_panel = new PicDescripPanel(pic_name, this.pic_rate);
        JLabel name_lable = new JLabel(pic_name);
        name_lable.setPreferredSize(new Dimension(300, 50));
        add(name_lable, BorderLayout.CENTER);
        add(pic_des_panel, BorderLayout.SOUTH);
        //setVisible(false);
    }
    public String getname(){
        return pic_name;
    }
    public int getRate(){
        return pic_rate;
    }
}

class Pic_MouseListener implements MouseInputListener{
    String p;
    String pic_name;
    PicDescripPanel pd;
    public Pic_MouseListener(String path, String n, PicDescripPanel pdd) {
        p = path;
        pic_name = n;
        pd = pdd;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        new Pic_Frame(p, pic_name, pd);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

class PicButton extends JButton{
    PicButton(String pic_name, PicDescripPanel pd){
        setBorderPainted(false);
        setContentAreaFilled(false);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 250));
        String p = "./src/imgs/" + pic_name;
        addMouseListener(new Pic_MouseListener(p, pic_name, pd));
        try{
            Image img = ImageIO.read(new File(p)).getScaledInstance(300, 250, Image.SCALE_DEFAULT);
            setIcon(new ImageIcon(img));
        } catch (Exception e){
            System.out.println(e);
        }

    }
}

class AlignLayout implements LayoutManager {

    int minimumSpacing;
    int preferredSpacing;

    /** Construct a new AlignLayout object with spacing between components. */
    public AlignLayout(int minSpacing, int preferredSpacing) {
        super();
        this.minimumSpacing = minimumSpacing;
        this.preferredSpacing = preferredSpacing;
    }

    // for specialized layouts, not used here
    public void addLayoutComponent(String name, Component comp) { }
    public void removeLayoutComponent(Component comp) {	}

    /**
     * Calculates the preferred size dimensions for the specified panel given
     * the components in the specified parent container.
     */
    public Dimension preferredLayoutSize(Container parent) {

        synchronized (parent.getTreeLock()) {

            // get space needed for all children
            Dimension space = calculateSpace(parent, true);

            // this container's padding
            Insets insets = parent.getInsets();

            Dimension d = new Dimension(insets.left + space.width +insets.right,
                    insets.top + space.height + insets.bottom);
            return d;
        }
    }


    /**
     * Calculates the minimum size dimensions for the specified panel given the
     * components in the specified parent container.
     */
    public Dimension minimumLayoutSize(Container parent) {

        synchronized (parent.getTreeLock()) {

            // get space needed for all children
            Dimension space = calculateSpace(parent, false);

            // this container's padding
            Insets insets = parent.getInsets();

            Dimension d = new Dimension(insets.left + space.width +insets.right,
                    insets.top + space.height + insets.bottom);
            return d;
        }
    }


    /**
     * Lays out the container in the specified panel.
     */
    public void layoutContainer(Container parent) {

        synchronized (parent.getTreeLock()) {

            // get space needed for all children (preferred)
            Dimension space = calculateSpace(parent, true);

            // this container's padding
            Insets insets = parent.getInsets();

            // get actual space available in parent
            int w = parent.getWidth() - insets.left - insets.right;
            int h = parent.getHeight() - insets.top - insets.bottom;

           // System.out.println("layoutContainer (parent size " + w + "," + h + ")");

            // vertical centre line to layout component
            int y = h / 2;

            // starting x is left side of all components to lay out
            int x = (w - space.width) / 2;

            int nComponents = parent.getComponentCount();
            for (int i = 0; i < nComponents; i++) {

                Component c = parent.getComponent(i);

                Dimension d = c.getPreferredSize();

                c.setBounds(x, y - d.height / 2, d.width, d.height);

                x += d.width + preferredSpacing;
            }

        }
    }


    /*
     * Precondition: the caller has gotten the treelock.
     */
    private Dimension calculateSpace(Container parent, boolean isPreferred) {

        // find total width for all components and
        // height of tallest component
        Dimension result = new Dimension(0,0);

        int nComponents = parent.getComponentCount();
        for (int i = 0; i < nComponents; i++) {

            Dimension d;
            if (isPreferred) {
                d = parent.getComponent(i).getPreferredSize();
            } else {
                d = parent.getComponent(i).getMinimumSize();
            }
            // update the total width and height required
            result.width += d.width;
            result.height = Math.max(result.height, d.height);
        }

        // add spacing in between components
        if (isPreferred) {
            result.width += (nComponents - 1) * preferredSpacing;
        } else {
            result.width += (nComponents - 1) * minimumSpacing;
        }

        return result;
    }

}