package com.computer.network.rdt1;


/**
 * rtd1.0 完全可靠信道的可靠数据传输 接收数据方
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年10月12日 下午2:39:09
 */
public class Receiver {
	//rdt通过rdt_rcv(packet)事件从底层信道接受一个分组，
	//从分组中取出数据(经由extract(packet,data)动作)，
	//并将数据上传给较高层(通过deliver_data(data)动作)
	public void rtd_rcv(Packet packet) {
		// extract(packet,data)
		String data = extract(packet);
		deliver_data(data);
	}

	public String extract(Packet packet) {
		return null;
	}

	public void deliver_data(String data) {

	}
}
