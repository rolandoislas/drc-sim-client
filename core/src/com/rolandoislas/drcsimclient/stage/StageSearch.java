package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.util.logging.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

import static com.rolandoislas.drcsimclient.Client.setStage;

/**
 * Created by rolando on 4/7/17.
 */
public class StageSearch extends StageList {

    private String hostname = "";
    private String error = "";

    public StageSearch() {
        setTitle("Searching for Servers");
        Thread searchThread = new Thread(new Runnable() {
            @Override
            public void run() {
                searchForServer();
            }
        });
        searchThread.setName("Network Search Thread");
        searchThread.start();
    }

    private void searchForServer() {
        // Get address of interfaces
        ArrayList<InetAddress> interfaceAddresses = new ArrayList<InetAddress>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    // Check ipv4 and not localhost
                    if (address.getHostAddress().contains(".") && !address.getHostAddress().startsWith("127.0.0."))
                        interfaceAddresses.add(address);
                }
            }
        } catch (SocketException e) {
            Logger.exception(e);
            error = "Failed to load network interfaces.";
            return;
        }
        // Try address ranges
        boolean foundServer = false;
        for (InetAddress address : interfaceAddresses) {
            for (int end = 1; end <= 255; end++) {
                InetAddress addressTry;
                try {
                    byte[] addressBytes = address.getAddress();
                    addressBytes[3] = (byte) end;
                    if (addressBytes == address.getAddress())
                        continue;
                    addressTry = InetAddress.getByAddress(addressBytes);
                    if (!addressTry.isReachable(10))
                        continue;
                } catch (UnknownHostException ignore) {
                    continue;
                } catch (IOException ignore) {
                    continue;
                }
                if (Client.connect(addressTry.getHostAddress()).isEmpty()) {
                    Client.sockets.dispose();
                    foundServer = true;
                    addItem(addressTry.getHostName(), new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            hostname = (String) ((List) actor).getSelected();
                        }
                    });
                }
            }
        }
        // Post
        if (!foundServer) {
            error = "Could not find a DRC Sim server.";
            return;
        }
        setTitle("Servers");
    }

    @Override
    public void onBackButtonPressed() {
        setStage(new StageConnect());
    }

    @Override
    public void act() {
        super.act();
        if (!error.isEmpty())
            setStage(new StageConnect(error));
        if (!hostname.isEmpty())
            setStage(new StageConnect(hostname, true));
    }
}
