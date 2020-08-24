package ir.kn.dblayer;

import ir.kn.entity.Message;
import ir.kn.entity.MyThread;
import ir.kn.entity.Subscription;
import ir.kn.entity.User;
import java.sql.Array;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBLayer {

    public static User getUser(String email, String password) throws SQLException {
        String query = "select * from user where email='" + email + "' and password='" + password + "'";
        System.out.println(query);
        ResultSet rs = DbHelper.executeQuery(query);
        User user = null;

        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setName(rs.getString("name"));
            user.setBio(rs.getString("bio"));
            user.setPhone(rs.getString("phone"));
            user.setEmail(rs.getString("email"));
            user.setLinks(rs.getString("links"));
            user.setImg(rs.getString("img"));

            fillSubscriberInfo(user);
        }

        return user;
    }

    public static User getUser(int userid) throws SQLException {
        String query = "select * from user where id=" + userid;
        System.out.println(query);
        ResultSet rs = DbHelper.executeQuery(query);
        User user = null;

        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setName(rs.getString("name"));
            user.setBio(rs.getString("bio"));
            user.setPhone(rs.getString("phone"));
            user.setEmail(rs.getString("email"));
            user.setLinks(rs.getString("links"));
            user.setImg(rs.getString("img"));

            fillSubscriberInfo(user);
        }

        return user;
    }

    public static void registerUser(User user) throws Exception {

       String query = "insert into user(username,name,bio,phone,email,links,password)"
                + " values('" + user.getUsername() + "', '" + user.getName() + "', '" + user.getBio()
                + "', '" + user.getPhone() + "', '" + user.getEmail() + "','" + user.getLinks() + "'"
                + ", '" + user.getPassword() + "')";
        int newUserId = DbHelper.executeUpdateAndReturnGeneratedKey(query);

        user.setId(newUserId);
    }
    
    public static void addImgToDb(User user) throws Exception{
    
        String query = "update user set img ='"
                + user.getImg() + "' where id =" + user.getId();
        
        DbHelper.executeQuery(query);
    }
    

    public static void rePost(String messageText, int userId) throws SQLException {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());


        String query = "INSERT INTO `message` (`sender_id`, `text`, `receiver_type`, `sending_time`) VALUES ('" +
                userId + "' , ' " + messageText + " ' , '1', '" + new Timestamp(calendar.getTimeInMillis()) + "')";
        

        DbHelper.executeQuery(query);
    }
    

    private static String getMessageById(int messageId) throws SQLException {
        String query = "select text from message where id=" + messageId + ";";

        ResultSet rs = DbHelper.executeQuery(query);
        return rs.next() ? rs.getString("text") : null;

    }

    public static void updateUser(User user) throws Exception {
        String query = "update user set username='" + user.getUsername() + "', name='" + user.getName()
                + "',bio='" + user.getBio() + "',phone='" + user.getPhone() + "',email='"
                + user.getEmail() + "',links='" + user.getLinks() + "' where id=" + user.getId();
        DbHelper.executeQuery(query);
    }

    public static List<User> getAllUsers(boolean followers) throws SQLException {

        List<User> users = new ArrayList<>();
        String query = "select * from user;";
        ResultSet rs = DbHelper.executeQuery(query);

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setName(rs.getString("name"));
            user.setBio(rs.getString("bio"));
            user.setPhone(rs.getString("phone"));
            user.setEmail(rs.getString("email"));
            user.setLinks(rs.getString("links"));

            if (followers)
                fillSubscribedInfo(user);
            else
                fillSubscriberInfo(user);

            users.add(user);
        }
        return users;
    }

    private static void fillSubscriberInfo(User user) throws SQLException {
        String query2 = "select * from subscription where subscriber_id=" + user.getId();
        ResultSet rs2 = DbHelper.executeQuery(query2);

        while (rs2.next()) {
            Subscription subscription = new Subscription();
            subscription.setSubscriberId(rs2.getInt("subscriber_id"));
            subscription.setSubscribedId(rs2.getInt("subscribed_id"));
            user.getSubscriptions().add(subscription);
        }
    }

    private static void fillSubscribedInfo(User user) throws SQLException {
        String query2 = "select * from subscription where subscribed_id=" + user.getId();
        ResultSet rs2 = DbHelper.executeQuery(query2);

        while (rs2.next()) {
            Subscription subscription = new Subscription();
            subscription.setSubscriberId(rs2.getInt("subscriber_id"));
            subscription.setSubscribedId(rs2.getInt("subscribed_id"));
            user.getSubscriptions().add(subscription);
        }
    }

    public static void deleteMessage(int messageId) throws SQLException {

        String query = "delete from message where id=" + messageId;
        DbHelper.executeQuery(query);

    }

    public static List<User> searchUsers(String name) throws SQLException {

        List<User> users = new ArrayList<>();
        String query = "select * from user where name like '%" + name + "%'";
        ResultSet rs = DbHelper.executeQuery(query);

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setName(rs.getString("name"));
            user.setBio(rs.getString("bio"));
            user.setPhone(rs.getString("phone"));
            user.setEmail(rs.getString("email"));
            user.setLinks(rs.getString("links"));
            users.add(user);
        }
        return users;
    }

    public static void subscribe(int subscriber_id, int subscribed_id) throws Exception {
        String query = "insert into subscription(subscriber_id, subscribed_id)"
                + " values(" + subscriber_id + ", " + subscribed_id + ")";
        System.out.println(query);
        int res = DbHelper.executeUpdate(query);
        if (res != 1) {
            throw new Exception("Failed to subscribe.");
        }
    }

    public static void sendMessage(Message msg) throws Exception {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        String query = "insert into message(sender_id, text, receiver_type, sending_time)"
                + " values (" + msg.getSender().getId() + ", '" + msg.getText() + "', '" + msg.getReceiverType()
                + "', '" + new Timestamp(calendar.getTimeInMillis()) + "' "
                + ")";
        System.out.println(query);
        int res = DbHelper.executeUpdateAndReturnGeneratedKey(query);
        msg.setId(res);
    }

    public static List<Message> getAllFollowingMessage(int userId) throws SQLException {
        String query = "select * from message where sender_id in ( select s.subscriber_id from user u join subscription s on s.subscribed_id=" + userId + ");";
        return fillMessagesList(query);
    }

    private static List<Message> fillMessagesList(String query) throws SQLException {
        List<Message> messages = new ArrayList<>();
       // ResultSet rs = null;
       ResultSet rs = DbHelper.executeQuery(query);

        MyThread thread = new MyThread(rs, query);
        thread.run();

        rs = thread.getRs();

        while (rs.next()) {
            Message msg = new Message();
            msg.setId(rs.getInt("id"));
            msg.setReceiverType(rs.getInt("receiver_type"));
            msg.setSendingTime(rs.getTimestamp("sending_time"));
            msg.setText(rs.getString("text"));
            msg.setSender(getUser(rs.getInt("sender_id")));
            messages.add(msg);
        }
        thread.stop();

        return messages;
    }

    public static List<Message> getAllPublicMessages() throws SQLException {

        String query = "select * from message where receiver_type=1 order by sending_time desc";
        return fillMessagesList(query);
    }

    public static List<Message> findUserPosts(int userId) throws SQLException {

        String query = "select * from message where sender_id = " + userId + ";";
        System.out.println(query + "\n" + "Trying to fetch all user's posts");

        return fillMessagesList(query);

    }


    public static List<Message> getInboxMessages(int userId) throws SQLException {

        String query = "select * from subscription join message on subscription.subscriber_id="+userId+" and subscription.subscribed_id=message.sender_id "
                + " where receiver_type=2 order by sending_time desc";
        System.out.println(query);
        
        List<Message> messages = fillMessagesList(query);
        //showing suscriber msg
        //messages.addAll(getAllFollowingMessage(userId));
        messages.sort((m1, m2) -> {
            if (m1.getSendingTime().before(m2.getSendingTime())) {
                return 1;
            } else if (m1.getSendingTime().after(m2.getSendingTime())) {
                return -1;
            } else {
                return 0;
            }
        });

        return messages;
    }

    public static int findUserByMessageId(int messageId) throws SQLException {
        String query = "select sender_id from message where id=" + messageId;
        ResultSet rs = DbHelper.executeQuery(query);
        return rs.next() ? rs.getInt("sender_id") : -1;
    }
    
    public static int[] getSubInfo(int UserId) throws SQLException{
        int[] arr = new int[20];
        int i = 0;
        String query = "select *  from subscription where subscriber_id=" + UserId;
        ResultSet rs = DbHelper.executeQuery(query);
        while(rs.next()){
        arr[i]=rs.getInt("subscribed_id");
        i++;
        }
          return arr;

}
}
