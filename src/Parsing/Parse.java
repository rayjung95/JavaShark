package Parsing;

import org.jnetpcap.Pcap;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;
import java.util.*;

//This is the parsing class. It loop all the wirshark file and get all the Ip sources by times, Ip destinations by times, and size of packets by hour


public class Parse {

    public ArrayList<Map> getData(String fileName) {

        final StringBuilder errbuf = new StringBuilder();
        final Pcap pcap = Pcap.openOffline(fileName, errbuf);
        HashMap<Long, String> sources = new HashMap<Long, String>();
        HashMap<Long, String> destinations = new HashMap<Long, String>();
        HashMap<String, Integer> SourceCount = new HashMap<>();
        HashMap<String, Integer> DestinationCount = new HashMap<>();
        HashMap<Long, String> TimeStamp = new HashMap<>();
        LinkedHashMap<String, Integer> SizeByHour = new LinkedHashMap<>();
        ArrayList<String> ArrivalTime = new ArrayList<>();
        ArrayList<Map> AllDataArray = new ArrayList<>();
        HashMap<Long, Integer> AllTime = new HashMap<Long, Integer>();
        ArrayList<Integer> hours = new ArrayList<>();
        ArrayList<Integer> mins = new ArrayList<>();
        ArrayList<Integer> secs = new ArrayList<>();



        //Adding 24 hours to the Treemap
        for ( int i = 0; i< 24; i++){
            SizeByHour.put(Integer.toString(i), 0);
        }

        if (pcap == null) {
            System.err.println(errbuf);
        }

        pcap.loop(pcap.LOOP_INFINITE, new JPacketHandler<StringBuilder>() {

            final Tcp tcp = new Tcp();
            final Udp udp = new Udp();
            final Http http = new Http();

            public void nextPacket(JPacket packet, StringBuilder errbuf) {

                if (packet.hasHeader(Udp.ID)) {

                    packet.getHeader(udp);

                    Ip4 ip = new Ip4();


                    if (packet.hasHeader(ip)) {


                        sources.put(packet.getFrameNumber(), FormatUtils.ip(ip.source()));
                        destinations.put(packet.getFrameNumber(), FormatUtils.ip(ip.destination()));

                        Date date = new Date(packet.getCaptureHeader().timestampInMillis());
                        String[] date2 = date.toString().split(" ");

                        String part4 = date2[3]; // time

                        String[] break1 = part4.split(":");


                        TimeStamp.put(packet.getFrameNumber(), part4);
                        AllTime.put(packet.getFrameNumber(), packet.getTotalSize());
                        if (SizeByHour.keySet().contains(break1[0])) {
                            SizeByHour.put(break1[0], SizeByHour.get(break1[0]) + packet.getTotalSize());
                        } else {
                            SizeByHour.put(break1[0], packet.getTotalSize());
                        }

                        ArrivalTime.add(part4);
                    }

                }

                if (packet.hasHeader(Tcp.ID)) {

                    packet.getHeader(tcp);

                    Ip4 ip = new Ip4();


                    if (packet.hasHeader(ip)) {

                        sources.put(packet.getFrameNumber(), FormatUtils.ip(ip.source()));
                        destinations.put(packet.getFrameNumber(), FormatUtils.ip(ip.destination()));

                        Date date = new Date(packet.getCaptureHeader().timestampInMillis());
                        String[] date2 = date.toString().split(" ");

                        String part4 = date2[3]; // time

                        String[] break1 = part4.split(":");


                        TimeStamp.put(packet.getFrameNumber(), part4);
                        AllTime.put(packet.getFrameNumber(), packet.getTotalSize());
                        if (SizeByHour.keySet().contains(break1[0])) {
                            SizeByHour.put(break1[0], SizeByHour.get(break1[0]) + packet.getTotalSize());
                        } else {
                            SizeByHour.put(break1[0], packet.getTotalSize());
                        }

                        ArrivalTime.add(part4);
                    }

                }

                if (packet.hasHeader(http.ID)) {

                    packet.getHeader(http);

                    Ip4 ip = new Ip4();


                    if (packet.hasHeader(ip)) {

                        sources.put(packet.getFrameNumber(), FormatUtils.ip(ip.source()));
                        destinations.put(packet.getFrameNumber(), FormatUtils.ip(ip.destination()));

                        Date date = new Date(packet.getCaptureHeader().timestampInMillis());
                        String[] date2 = date.toString().split(" ");

                        String part4 = date2[3]; // time

                        String[] break1 = part4.split(":");


                        TimeStamp.put(packet.getFrameNumber(), part4);
                        AllTime.put(packet.getFrameNumber(), packet.getTotalSize());
                        if (SizeByHour.keySet().contains(break1[0])) {
                            SizeByHour.put(break1[0], SizeByHour.get(break1[0]) + packet.getTotalSize());
                        } else {
                            SizeByHour.put(break1[0], packet.getTotalSize());
                        }

                        ArrivalTime.add(part4);
                    }

                }

            }

        }, errbuf);

        for (String value : sources.values()) {

            if (SourceCount.keySet().contains(value)) {
                SourceCount.put(value, SourceCount.get(value) + 1);

            } else {
                SourceCount.put(value, 1);
            }
        }
        AllDataArray.add(SourceCount);

        for (String value : destinations.values()) {

            if (DestinationCount.keySet().contains(value)) {
                DestinationCount.put(value, DestinationCount.get(value) + 1);

            } else {
                DestinationCount.put(value, 1);
            }
        }
        AllDataArray.add(DestinationCount);


        for (String time : ArrivalTime) {
            String[] timeSplit = time.split(":");
            if (!hours.contains(Integer.valueOf(timeSplit[0]))) {
                hours.add(Integer.valueOf(timeSplit[0]));
            }
            if (!mins.contains(Integer.valueOf(timeSplit[1]))) {
                mins.add(Integer.valueOf(timeSplit[1]));
            }
            secs.add(Integer.valueOf(timeSplit[2]));

        }

        Collections.sort(secs);
        Collections.sort(hours);
        Collections.sort(mins);

        AllDataArray.add(SizeByHour);


        pcap.close();
        return AllDataArray;
    }


}