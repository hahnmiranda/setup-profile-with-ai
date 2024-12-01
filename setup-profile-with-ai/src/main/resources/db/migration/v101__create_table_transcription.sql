CREATE TABLE `transcription` (   `id` bigint NOT NULL AUTO_INCREMENT,
                                 `customer_id` bigint DEFAULT NULL,
                                 `upload_date` datetime(6) DEFAULT NULL,
                                 `user_account_id` bigint DEFAULT NULL,
                                 `file_hash` varchar(255) DEFAULT NULL,
                                 `file_name` varchar(255) NOT NULL,
                                 PRIMARY KEY (`id`)
);