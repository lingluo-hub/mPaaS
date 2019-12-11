package com.ibyte.attach.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 附件底层文件信息
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Setter
@Getter
@Entity
@Table(name = "sys_attach_file", indexes = @Index(columnList = "fdMd5, fdFileSize, fdFileName, fdFileExtName"))
public class SysAttachFile extends SysAttachFileBase {


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "fdSysAttachFile")
    @OrderBy(value = "fdCreateTime")
    private List<SysAttachMain> fdSysAttachMain;

}