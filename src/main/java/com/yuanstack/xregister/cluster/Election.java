package com.yuanstack.xregister.cluster;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * elect for servers.
 *
 * @author Sylvan
 * @date 2024/05/14  22:43
 */
@Slf4j
public class Election {
    public void electLeader(List<Server> servers) {
        List<Server> masters = servers.stream().filter(Server::isStatus)
                .filter(Server::isLeader).toList();
        if (masters.isEmpty()) {
            log.warn(" ===>>> [ELECT] elect for no leader: " + servers);
            elect(servers);
        } else if (masters.size() > 1) {
            log.warn(" ===>>> [ELECT] elect for more than one leader: " + servers);
            elect(servers);
        } else {
            log.debug(" ===>>> no need election for leader: " + masters.get(0));
        }
    }

    private void elect(List<Server> servers) {
        // 1.各种节点自己选，算法保证大家选的是同一个
        // 2.外部有一个分布式锁，谁拿到锁，谁是主
        // 3.分布式一致性算法，比如paxos,raft，，很复杂
        Server candidate = null;
        for (Server server : servers) {
            server.setLeader(false);
            if (server.isStatus()) {
                if (candidate == null) {
                    candidate = server;
                } else {
                    if (candidate.hashCode() > server.hashCode()) {
                        candidate = server;
                    }
                }
            }
        }
        if (candidate != null) {
            candidate.setLeader(true);
            log.info(" ===>>> elect for leader: " + candidate);
        } else {
            log.info(" ===>>> elect failed for no leaders: " + servers);
        }
    }
}