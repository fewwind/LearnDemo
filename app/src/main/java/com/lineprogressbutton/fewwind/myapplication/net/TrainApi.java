package com.lineprogressbutton.fewwind.myapplication.net;

import com.lineprogressbutton.fewwind.myapplication.bean.TrainStationInfo;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 2016/8/26.
 */
public interface TrainApi {
    //https://kyfw.12306.cn
    //https://kyfw.12306.cn/otn/leftTicket/queryT?leftTicketDTO.train_date=2016-09-24&leftTicketDTO.from_station=BJP&leftTicketDTO.to_station=CSQ&purpose_codes=ADULT
    //leftTicketDTO.train_date=2016-09-24&leftTicketDTO.from_station=BJP&leftTicketDTO.to_station=CSQ&purpose_codes=ADULT
    @GET("leftTicket/queryT") Observable<TrainStationInfo> getStationInfo(
        @Query("leftTicketDTO.train_date") String date,
        @Query("leftTicketDTO.from_station") String fromSattion,
        @Query("leftTicketDTO.to_station") String toSattion,
        @Query("purpose_codes") String purpose);
}
