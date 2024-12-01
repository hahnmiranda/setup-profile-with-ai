CREATE TABLE `profile` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `customer_id` bigint NOT NULL,
                           `name` varchar(255) NOT NULL,
                           `is_comptime` bit(1) DEFAULT NULL,
                           PRIMARY KEY (`id`)
);