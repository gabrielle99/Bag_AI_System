package com.tianci.model.media.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Setter
@Getter
@Document(collection = "tc_media")
public class ImagePojo {
    // Will auto generate tables
    @Id
    private String fileId;
    //File Request Path
    private String filePath;
    //File Size
    private long fileSize;
    //File Name
    private String fileName;
    //File Type
    private String fileType;
    //Image Width
    private int fileWidth;
    //Image Height
    private int fileHeight;
    //user id, used for authorization
    private String userId;
    //business key
    private String businesskey;
    //tag
    private String filetag;
    //file's metadata
    private Map metadata;

    private String predictedResult;
}
