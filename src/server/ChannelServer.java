package server;

import inter.IChannel;

import java.util.ArrayList;

public class ChannelServer {

    private static ArrayList<IChannel> channels;

    public static ArrayList<IChannel> getChannels() {
        return channels;
    }

    public static int size(){
        return channels.size();
    }

    public static void init(){
        channels = new ArrayList<>();
    }
}
