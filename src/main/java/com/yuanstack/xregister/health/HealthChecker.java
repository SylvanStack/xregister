package com.yuanstack.xregister.health;

/**
 * Interface for health checker.
 *
 * @author Sylvan
 * @date 2024/05/05  21:56
 */
public interface HealthChecker {
    void start();

    void stop();
}
