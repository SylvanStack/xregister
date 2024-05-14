package com.yuanstack.xregister.cluster;

import com.yuanstack.xregister.model.InstanceMeta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Map;

/**
 * Description for this class.
 *
 * @author Sylvan
 * @date 2024/05/14  22:52
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Snapshot {
    LinkedMultiValueMap<String, InstanceMeta> REGISTRY;
    Map<String, Long> VERSIONS;
    Map<String, Long> TIMESTAMPS;
    long version;
}
