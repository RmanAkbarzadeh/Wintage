package ir.kn.panels;

import ir.kn.dblayer.DBLayer;
import ir.kn.entity.Message;
import ir.kn.entity.Subscription;
import ir.kn.entity.User;
import ir.kn.utilities.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.JTableHeader;

class ProfilePanel extends SNJPanel implements SetMaterialGf{

    private JTextField email;
    private JTextField username;
    private JTextField bio;
    private JTextField links;
    private JTextField phone;
    private JTextField name;
    private JLabel message;
    private JTable table;
    private JScrollPane scrollPane;

    private JLabel posts;
    private JButton following;
    private JButton followers;
    private int countOfPosts;

    private static JButton delete;
    private static String[] columnNames;
    private static JPanel rootPanel;
    
    List<Subscription> subscriptions = new ArrayList<>();
    
    
    private JLabel imglab;
    private JButton button ;
    private JLabel label;


    private User myUser;

    ProfilePanel() {
        setLayout(null);
        setSize(860, 600);

        rootPanel = new JPanel();
        rootPanel.setLayout(null);
        rootPanel.setBounds(10, 245, 830, 288);
        add(rootPanel);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 830, 288);
        rootPanel.add(scrollPane);

        DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        columnNames = new String[]{"User",
                "Message",
                "Time",
                "delete"};
        for (String s : columnNames) {
            model.addColumn(s);
        }

        delete = new JButton("delete");

        table = new JTable(model);
        scrollPane.setViewportView(table);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setBackground(new Color(17,33,68));
        btnRefresh.setForeground(Color.white);
        setCircleButton(btnRefresh);
        btnRefresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                displayProfileMessages(Objects.requireNonNull(getAllUsersMessages()));
                
            }
        });
        btnRefresh.setBounds(10, 210, 124, 23);
        add(btnRefresh);

        
    //photo        
        button = new JButton("Browse");
        button.setBounds(410,5,160,40);
        setCircleButton(button);
        label = new JLabel();
        label.setBounds(600, 30, 150, 150);
        add(button);
        add(label);
    
        button.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
        
          JFileChooser file = new JFileChooser();
          file.setCurrentDirectory(new File(System.getProperty("user.home")));
          file.addChoosableFileFilter(new ImageFilter());
          file.setAcceptAllFileFilterUsed(false);
          int result = file.showSaveDialog(null);
          if(result == JFileChooser.APPROVE_OPTION){
              File selectedFile = file.getSelectedFile();
              String path = selectedFile.getAbsolutePath();
              try {
                  getPathAndInsetToDb(path);
              } catch (Exception ex) {
                  Logger.getLogger(ProfilePanel.class.getName()).log(Level.SEVERE, null, ex);
              }
              label.setIcon(ResizeImage(path));
          }
          


          else if(result == JFileChooser.CANCEL_OPTION){
              System.out.println("No File Select");
          }
        }
    });
    

    


        // containing followers and following and posts elements
        BorderLayout layout = new BorderLayout();
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(29, 161, 242));
        infoPanel.setLayout(layout);
        infoPanel.setBounds(410, 205, 430, 25);
        add(infoPanel);



        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(29, 161, 242));
        panel_1.setLayout(null);
        panel_1.setBounds(0, 5, 491, 279);
        add(panel_1);
        
                JLabel nameLb = new JLabel("Name:");
		nameLb.setBounds(62, 14, 82, 14);
                nameLb.setForeground(Color.white);
                setBigFont(nameLb);
		panel_1.add(nameLb);
                
                JLabel EmailLb = new JLabel("Email:");
		EmailLb.setBounds(62, 39, 82, 14);
                EmailLb.setForeground(Color.white);
                setBigFont(EmailLb);
		panel_1.add(EmailLb);
                
                
		JLabel UsernameLb = new JLabel("Username:");
		UsernameLb.setBounds(62, 63, 82, 14);
                UsernameLb.setForeground(Color.white);
                setBigFont(UsernameLb);
		panel_1.add(UsernameLb);
		
		
		JLabel BioLb = new JLabel("bio:");
		BioLb.setBounds(62, 88, 82, 14);
                BioLb.setForeground(Color.white);
                setBigFont(BioLb);
		panel_1.add(BioLb);
		
		JLabel LinksLb = new JLabel("links:");
		LinksLb.setBounds(62, 113, 82, 16);
                LinksLb.setForeground(Color.white);
                setBigFont(LinksLb);
		panel_1.add(LinksLb);
		
                
                JLabel PhoneLb = new JLabel("Phone:");
		PhoneLb.setBounds(62, 138, 82, 14);
                PhoneLb.setForeground(Color.white);
                setBigFont(PhoneLb);
		panel_1.add(PhoneLb);


        followers = new JButton("Followers");
        followers.setBackground(new Color(17,33,68));
        followers.setForeground(Color.white);
        setCircleButton(followers);
        infoPanel.add(followers, BorderLayout.CENTER);
        followers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
 
                try {
                    showFollow(false);
                } catch (SQLException ex) {
                    Logger.getLogger(ProfilePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        following = new JButton("Following");
        following.setBackground(new Color(17,33,68));
        following.setForeground(Color.white);
        setCircleButton(following);
        infoPanel.add(following, BorderLayout.EAST);
        following.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                try {
                    showFollow(true);
                } catch (SQLException ex) {
                    Logger.getLogger(ProfilePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        posts = new JLabel();
        posts.setBackground(new Color(255, 255, 155));
        infoPanel.add(posts, BorderLayout.WEST);


        email = new JTextField();
        email.setColumns(10);
        email.setBounds(150, 36, 250, 20);
        panel_1.add(email);

        username = new JTextField();
        username.setColumns(10);
        username.setBounds(150, 63, 250, 20);
        panel_1.add(username);

        bio = new JTextField();
        bio.setColumns(10);
        bio.setBounds(150, 88, 250, 20);
        panel_1.add(bio);

        links = new JTextField();
        links.setColumns(10);
        links.setBounds(150, 113, 250, 20);
        panel_1.add(links);

        phone = new JTextField();
        phone.setColumns(10);
        phone.setBounds(150, 138, 250, 20);
        panel_1.add(phone);

        JButton btnSave = new JButton("Save");
        btnSave.setBackground(Color.GREEN);
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                
                boolean check = Validate();
                              
                    if(check == true){
                        
                        save();
                    }
            }
        });
        btnSave.setBounds(210, 200, 89, 23);
        panel_1.add(btnSave);

        JButton button_1 = new JButton("Cancel");
        button_1.setBackground(Color.RED);
        button_1.setBounds(309, 200, 89, 23);
        panel_1.add(button_1);


        name = new JTextField();
        name.setColumns(10);
        name.setBounds(150, 11, 250, 20);
        panel_1.add(name);

        message = new JLabel("");
        message.setBounds(150, 169, 250, 14);
        panel_1.add(message);


    }
    
     public ImageIcon ResizeImage(String ImagePath)
    {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }

    private void showFollow(boolean following) throws SQLException {

        DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] columnNames = {"Name",
                "Phone",
                "UserName"};

        for (String s : columnNames) {
            model.addColumn(s);
        }

        model.setRowCount(0);

        if (following)
            followingTableData(model);
        else
            followersTableData(model);

        JFrame parent = new JFrame("follow info");
        parent.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        JTable myTable = new JTable(model);

        myTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        myTable.getColumnModel().getColumn(1).setPreferredWidth(160);
        myTable.getColumnModel().getColumn(2).setPreferredWidth(150);

        panel.add(new JScrollPane(myTable));

        parent.add(panel);
        parent.pack();
        parent.setVisible(true);

    }

    private void followersTableData(DefaultTableModel model) {
        try {
            List<User> allUsers = new ArrayList<>(DBLayer.getAllUsers(true));
            List<Subscription> subscriptions = new ArrayList<>();
            model.setRowCount(0);
            allUsers.forEach(a -> {
                subscriptions.clear();
                subscriptions.addAll(a.getSubscriptions());
                subscriptions.forEach(s -> {
                    if (s.getSubscribedId() == myUser.getId()) {
                        try {
                            model.addRow(new Object[]{
                                    (DBLayer.getUser(s.getSubscriberId())).getName(),
                                    (DBLayer.getUser(s.getSubscriberId())).getPhone(),
                                    (DBLayer.getUser(s.getSubscriberId())).getUsername()
                            });
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
            });

        } catch (SQLException e) {
            System.out.println("unable to fetch user");
        }
    }

    private void followingTableData(DefaultTableModel model) throws SQLException {                  


        
     //   List<Subscription> subscriptions = new ArrayList<>(myUser.getSubscriptions());
        List<Subscription> subscriptions = new ArrayList<>();
        int[] arr = new int[20];
        arr = DBLayer.getSubInfo(myUser.getId());
        
        for(int i = 0 ; i<arr.length ; i++){
            if(arr[i] != 0){
             Subscription sub = new Subscription();
             sub.setSubscribedId(arr[i]);
             sub.setSubscriberId(myUser.getId());
             subscriptions.add(sub);
            }
        }
        
                
        System.out.println("injam");
        model.setRowCount(0);
        
        for(Subscription s : subscriptions){
                        if (s.getSubscriberId() == myUser.getId()) {
                try {
                    model.addRow(new Object[]{
                            DBLayer.getUser(s.getSubscribedId()).getName(),
                            DBLayer.getUser(s.getSubscribedId()).getPhone(),
                            DBLayer.getUser(s.getSubscribedId()).getUsername()});
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        System.out.println("Tedad :" + subscriptions.get(0).getSubscribedId());
        
        }

    }

    private void save() {

        User updatedUser = new User();
        updatedUser.setBio(bio.getText());
        updatedUser.setLinks(links.getText());
        updatedUser.setEmail(email.getText());
        updatedUser.setId(getRootFrame().getSession().getUser().getId());
        updatedUser.setName(name.getText());
        updatedUser.setPhone(phone.getText());
        updatedUser.setUsername(username.getText());

        try {
            DBLayer.updateUser(updatedUser);
            getRootFrame().getSession().setUser(updatedUser);
            message.setText("Successfully updated.");
        } catch (Exception e) {
            e.printStackTrace();
            message.setText(e.getMessage());
        }
    }

    private List<Message> getAllUsersMessages() {
        User user = getRootFrame().getSession().getUser();
        try {
            return DBLayer.findUserPosts(user.getId());
        } catch (SQLException e) {
            System.out.println("User's messages didn't fetch");
            return null;
        }
    }

    void init() throws IOException {
        
        myUser = getRootFrame().getSession().getUser();
        name.setText(myUser.getName());
        email.setText(myUser.getEmail());
        username.setText(myUser.getUsername());
        bio.setText(myUser.getBio());
        links.setText(myUser.getLinks());
        phone.setText(myUser.getPhone());
        
        if(myUser.getImg()!= null){
           
           label.setIcon(ResizeImage(myUser.getImg()));
            
        }
        

        countOfPosts = 0;
        List<Message> messageList = getAllUsersMessages();
        Objects.requireNonNull(messageList).forEach(m -> {
            if (m.getSender().getId() == myUser.getId())
                countOfPosts++;
        });
        posts.setText("    posts: " + countOfPosts + "       ");

        displayProfileMessages(Objects.requireNonNull(getAllUsersMessages()));
    }

    private void displayProfileMessages(List<Message> messages) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        	
                table.setBackground(new Color(20, 117, 181));
                table.setGridColor(new Color(20, 117, 181));
                table.setForeground(Color.white);
                table.setRowHeight(40);
                table.setSelectionBackground(new Color(17,33,68));
                table.setSelectionForeground(Color.WHITE);
                table.setIntercellSpacing(new Dimension(15,10));
                table.setFont(new Font("Serif", Font.BOLD, 13));
                setLocation(new Point(700,300));
                
                JTableHeader tableHeader = table.getTableHeader();
                tableHeader.setBackground(new Color(17,33,68));
                tableHeader.setForeground(Color.white);
                tableHeader.setPreferredSize(new Dimension(30,30));
                tableHeader.setFont(new Font("Serif", Font.BOLD, 13));

        for (Message m : messages) {
            model.addRow(new Object[]{
                    m.getSender().getName(),
                    m.getText(),
                    m.getSendingTime(),
                    "delete"
            });
        }

        Action delete = new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {

                    int rowIndex = ((JTable) actionEvent.getSource()).getSelectedRow();

                    System.out.println("Row number = " + rowIndex);
                    int id = messages.get(rowIndex).getId();

                    DBLayer.deleteMessage(id);
                    message.setText("Successfully deleted");
                     

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    message.setText("unable to delete message");
                }
            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(table, delete, 3, true, false);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(460);
        table.getColumnModel().getColumn(2).setPreferredWidth(130);
        table.getColumnModel().getColumn(3).setPreferredWidth(55);

    }
    



//   public JPanel createWindow() {    
//      JFrame frame = new JFrame("Swing Tester");
//      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      JPanel panel = createUI(frame);
//      frame.setSize(960, 200);      
//      frame.setLocationRelativeTo(null);  
//      return panel;
//   }
//
//   public JPanel createUI(final JFrame frame){  
//      JPanel panel = new JPanel();
//      LayoutManager layout = new FlowLayout();  
//      panel.setLayout(layout);       
//      JButton button = new JButton("Choose");
//      final JLabel label = new JLabel();
//      
//      button.addActionListener(new ActionListener() {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.addChoosableFileFilter(new ImageFilter());
//            fileChooser.setAcceptAllFileFilterUsed(false);
//
//            int option = fileChooser.showOpenDialog(frame);
//            if(option == JFileChooser.APPROVE_OPTION){
//               File file = fileChooser.getSelectedFile();
//               label.setText("File Selected: " + file.getName());
//               String path = file.getPath();
//                
//                try {
//                    
//                   JLabel label1 = LoadImage(path);
//                   panel.add(label1);
//                   getPathAndInsetToDb(file.getPath());
//                } catch (IOException ex) {
//                    Logger.getLogger(ProfilePanel.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (Exception ex) {
//                    Logger.getLogger(ProfilePanel.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }else{
//               label.setText("Open command canceled");
//            }
//         }
//      });
//
//      panel.add(button);
//      panel.add(label);
//      frame.getContentPane().add(panel, BorderLayout.CENTER);
//      return panel;
//   }  
   
   
   public void getPathAndInsetToDb(String path) throws Exception{
       
       User user = DBLayer.getUser(myUser.getId());
       user.setImg(path);
       DBLayer.addImgToDb(user);
       
   }
   
//   public void drawImg() throws IOException{
//       
//       imglab = new JLabel();
//       
//       imglab = LoadImage(myUser.getImg());
//            
//       add(imglab);
//   }
   
//   public void draw(){
//       
//        picturePanel = new JPanel();
//        picturePanel.removeAll();
//        BorderLayout layout = new BorderLayout();
//        picturePanel.setLayout(layout);
//        picturePanel = createWindow();
//        picturePanel.setBounds(410, 5, 430, 160);
//        add(picturePanel);
//        revalidate();
//        repaint();
//        //return picturePanel;
//   }
   protected boolean Validate(){
            
            boolean check = true;
            
              if(!name.getText().matches("[A-Z][a-zA-Z]*")){
                  
                  this.message.setText("Please enter a valid name");
                  
                   check = false;
              }
              
              if(!email.getText().matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")){
                  
                  if(check == false){
                      
                      this.message.setText(this.message.getText().concat(",email"));

                  }else{
                                   
                        this.message.setText("Please enter a valid email");
                        check = false;
                        }
              }
              
              if(!username.getText().matches("^[a-z0-9_-]{3,15}$")){
                  
                   if(check == false){
                      
                      this.message.setText(this.message.getText().concat(",username"));

                   }else{
                            this.message.setText("Please enter a valid username");
                             check = false;
                        }
              }
              

              
              if(!phone.getText().matches("^[0-9]{11}$")){
                  
                   if(check == false){
                      
                      this.message.setText(this.message.getText().concat(",phone."));

                   }else{
                            this.message.setText("Please enter a valid phone");
                             check = false;
                        }
              }
              
              
             return check;
        }
   
       private static class RoundedBorder implements Border {

           private int radius;


           RoundedBorder(int radius) {
               this.radius = radius;
            }


           public Insets getBorderInsets(Component c) {
                return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
            }


           public boolean isBorderOpaque() {
                return true;
            }


            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.drawRoundRect(x, y, width-1, height-1, radius, radius);
             }
      }

    @Override
    public void setBigFont(JLabel jlabel) {
                Font labelFont = jlabel.getFont();
                String labelText = jlabel.getText();
                int stringWidth = jlabel.getFontMetrics(labelFont).stringWidth(labelText);
                int componentWidth = jlabel.getWidth();
                double widthRatio = (double)componentWidth / (double)stringWidth;
                int newFontSize = (int)(labelFont.getSize() * widthRatio);
                int componentHeight = jlabel.getHeight();
                int fontSizeToUse = Math.min(newFontSize, componentHeight);
                jlabel.setFont(new Font(labelFont.getName(), Font.LAYOUT_NO_START_CONTEXT, fontSizeToUse));
    }

    @Override
    public void setCircleButton(JButton jbutton) {
      // jbutton.setBorder(new RoundedBorder(50));
                jbutton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
    }
} 

class ImageFilter extends FileFilter {
   public final static String JPEG = "jpeg";
   public final static String JPG = "jpg";
   public final static String GIF = "gif";
   public final static String TIFF = "tiff";
   public final static String TIF = "tif";
   public final static String PNG = "png";
   
   @Override
   public boolean accept(File f) {
      if (f.isDirectory()) {
         return true;
      }

      String extension = getExtension(f);
      if (extension != null) {
         if (extension.equals(TIFF) ||
            extension.equals(TIF) ||
            extension.equals(GIF) ||
            extension.equals(JPEG) ||
            extension.equals(JPG) ||
            extension.equals(PNG)) {
            return true;
         } else {
            return false;
         }
      }
      return false;
   }

   @Override
   public String getDescription() {
      return "Image Only";
   }

   String getExtension(File f) {
      String ext = null;
      String s = f.getName();
      int i = s.lastIndexOf('.');
   
      if (i > 0 &&  i < s.length() - 1) {
         ext = s.substring(i+1).toLowerCase();
      }
      return ext;
   }
  
}
