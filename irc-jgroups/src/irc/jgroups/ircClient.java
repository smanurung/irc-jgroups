/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irc.jgroups;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

/**
 *
 * @author FUJITSU
 */
public class ircClient extends ReceiverAdapter {
    
    private String nickname;
    private LinkedList<JChannel> channelL;
    final List<String> state = new LinkedList<>();

    public LinkedList<JChannel> getChannelL() {
        return channelL;
    }

    public void setChannelL(LinkedList<JChannel> channelL) {
        this.channelL = channelL;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    @Override
    public void getState(OutputStream output) throws Exception
    {
        synchronized (state)
        {
            Util.objectToStream(state, new DataOutputStream(output));
        }
    }
    
    @Override
    public void setState(InputStream input) throws Exception
    {
        List<String> list;
        list = (List<String>) Util.objectFromStream(new DataInputStream(input));
        synchronized (state)
        {
            state.clear();
            state.addAll(list);
        }
        System.out.println(list.size()+" messages in chat history");
        for (String s : list)
        {
            System.out.println(s);
        }
    }
    
    @Override
    public void viewAccepted(View new_view)
    {
        System.out.println("** View: "+new_view);
    }
    
    @Override
    public void receive(Message msg)
    {
        String line = msg.getObject().toString();
        System.out.println(line);
        synchronized (state)
        {
            state.add(line);
        }
    }
    
    public ircClient(){
        this.nickname = "027-defuser";
        this.channelL = new LinkedList<>();
    }
    
    public void start() throws Exception
    {        
        String input;
        Scanner scanner = new Scanner(System.in);
        
        while(true)
        {
            System.out.print("> ");
            input = scanner.nextLine();
            
            String[] command = input.split(" ");
            
            if(command[0].startsWith("/EXIT"))
            {
                System.out.println("program stopped");
                break;
            }
            else if(command[0].startsWith("/NICK"))
            {
                if(command.length > 1)
                {
//                    assign with given username
                    setNickname(command[1]);
                    
//                    response
                    System.out.println("[SUCCESS] user has nickname to '"+ nickname +"'");
                }
                else
                {
//                    generate random username
                    setNickname("027-defuser");
                    
//                    response
                    System.out.println("[SUCCESS] system has generate random username");
                }
            }
            else if(command[0].startsWith("/JOIN"))
            {
                if(command.length > 1)
                {
                    String clusterName = command[1];
                    JChannel channel = new JChannel();
                    channel.setReceiver(this);
                    channel.connect(clusterName);
                    
//                    no self-message
                    channel.setDiscardOwnMessages(true);
                    
//                    get current cluster history
                    channel.getState(null, 1000);
                    
//                    add channel list
                    channelL.add(channel);
                    
//                    response
                    System.out.println("[SUCCESS] user has joined cluster '"+ clusterName +"'");
                }
                else
                {
                    System.out.println("[WARNING] join command should be followed by channel name");
                }
            }
            else if (command[0].startsWith("/LEAVE"))
            {
                if(command.length > 1)
                {
                    
                }
                else
                {
                    System.out.println("[WARNING] leave command should be followed by channel name");
                }
            }
            else if (command[0].startsWith("@"))
            {
//                send to specific channel
            }
            else
            {
//                broadcast to all channel
            }
        }
    }
}