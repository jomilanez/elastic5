package com.jomilanez.repository;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * KinesisEvent sent by user when interacting with an article in the app.
 *
 * Fields can be null.
 */
@ToString
@Getter
public class Event {

    private final String id;
    private final String deviceuserUid;
    private final String imei;
    private final String eventType;
    /**
     * User id, in most cases same value as {@link #deviceuserUid}. Can have different value only if user used one of our SSO mechanisms on multiple devices
     */
    private String userProfileId;

    @Builder
    @JsonCreator
    public Event(@JsonProperty("id") String id,
                 @JsonProperty("eventType") String eventType,
                 @JsonProperty("deviceuserUid") String deviceuserUid,
                 @JsonProperty("imei") String imei,
                 @JsonProperty("userProfileId") String userProfileId) {
        this.id = id;
        this.eventType = eventType;
        this.deviceuserUid = deviceuserUid;
        this.imei = imei;
        this.userProfileId = userProfileId;
    }

}
