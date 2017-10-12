package com.computer.network.rdt1;


/**
 * rtd1.0 完全可靠信道的可靠数据传输 发送数据方
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年10月12日 下午12:19:38
 */
public class Sender {
	
	
	
	// 上面一层调用
	// rdt的发送端通过rtd_send(data)事件接受来自较高层的数据，
	// 产生一个包含该数据的分组(经由make_pkt(data)动作)，
	// 并将分组发送到信道中
	public void rtd_send(String data) {
		Packet packet = make_pkt(data);
		udt_send(packet);
	}

	public Packet make_pkt(String data) {
		return new Packet(data);
	}

	public void udt_send(Packet packet) {
		
	}

}
