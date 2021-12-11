USE `website_hotels_db`;

CREATE TABLE `countries` (
                             `code` CHAR(3) NOT NULL UNIQUE PRIMARY KEY,
                             `name` CHAR(52) NOT NULL
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `users` (
                         `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
                         `email` VARCHAR(255) NOT NULL UNIQUE,
                         `password` NCHAR(64),

                         `role` TINYINT NOT NULL CHECK (`role` IN (0, 1, 2)),
                         PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `tour_organization_info` (
                              `user_id` INTEGER UNSIGNED NOT NULL PRIMARY KEY,
                              `tour_organization` VARCHAR(100) NOT NULL,
                              `license` VARCHAR(50) NOT NULL,
                              CONSTRAINT FK_tour_organization_users FOREIGN KEY (`user_id`)
                                  REFERENCES `users` (`id`)
                                  ON UPDATE CASCADE
                                  ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `users_info` (
                             `user_id` INTEGER UNSIGNED NOT NULL PRIMARY KEY,
                             `name` VARCHAR(20) NOT NULL,
                             `surname` VARCHAR(30) NOT NULL,
                             `middle_name` VARCHAR(30),
                             `phone` BIGINT NOT NULL,
                             `passport` CHAR(9) UNIQUE NOT NULL,
                             `date_of_birthday` DATE NOT NULL,
                             `sex` BOOLEAN NOT NULL, /* 0 - man, 1 - woman */
                             `code_country` CHAR(3) NOT NULL,
                             CONSTRAINT FK_users_info_users FOREIGN KEY (`user_id`)
                                 REFERENCES `users` (`id`)
                                 ON UPDATE CASCADE
                                 ON DELETE RESTRICT,
                             CONSTRAINT FK_users_info_countries FOREIGN KEY (`code_country`)
                                 REFERENCES `countries` (`code`)
                                 ON UPDATE CASCADE
                                 ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `hotels` (
                          `id` INTEGER NOT NULL PRIMARY KEY,
                          `admin_id` INTEGER UNSIGNED NOT NULL,
                          `name` VARCHAR(50) NOT NULL,
                          `stars` TINYINT NOT NULL CHECK ( `stars` <= 5),
                          `type_of_food` TINYINT NOT NULL CHECK (`type_of_food` < 32),
                          #no, breakfast, half board,full board,all
                          `type_of_allocation` TINYINT NOT NULL CHECK (`type_of_allocation`< 32),
                          #single,double,triple,extra (4 people), child (1 people)
                          `type_of_comfort` TINYINT NOT NULL CHECK  (`type_of_comfort` > 0 AND `type_of_comfort` < 16),
                          #standard,family,luxe,suite
                          `price_of_room` VARCHAR(25) NOT NULL,
                          #standard price for rooms
                          `price_of_comfort` VARCHAR(15) NOT NULL,
                         #add price because of comfort
                          #max 99999$ for 1 room for 1 night
                          `reward_for_tour_operator` VARCHAR(20) NOT NULL,
                          #in money or in percent (100%)
                          `parking` BOOLEAN NOT NULL,
                          `wifi` BOOLEAN NOT NULL,
                          `pets` BOOLEAN NOT NULL,
                          `business_center` BOOLEAN NOT NULL,
                          `countries_id` CHAR (3) NOT NULL,
                          `city` VARCHAR(20) NOT NULL ,
                          `street` VARCHAR(50) NOT NULL ,
                          `house` SMALLINT,
                          `building` TINYINT,
                          CONSTRAINT FK_hotels_countries FOREIGN KEY (`countries_id`)
                              REFERENCES `countries` (`code`)
                              ON UPDATE CASCADE
                              ON DELETE RESTRICT,
                          CONSTRAINT FK_hotels_users FOREIGN KEY (`admin_id`)
                              REFERENCES `users` (`id`)
                              ON UPDATE CASCADE
                              ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `rooms` (
                          `number` INTEGER NOT NULL,
                          `hotel_id` INTEGER NOT NULL,
                          `type_of_allocation` TINYINT NOT NULL CHECK (`type_of_allocation` > 0 AND `type_of_allocation`< 32),
                          `type_of_comfort` TINYINT NOT NULL CHECK (`type_of_comfort` < 16),
                          CONSTRAINT FK_rooms_hotels FOREIGN KEY (`hotel_id`)
                              REFERENCES `hotels` (`id`)
                              ON UPDATE CASCADE
                              ON DELETE RESTRICT,
                          CONSTRAINT PK_rooms PRIMARY KEY (`number`,`hotel_id`)
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `booking` (
    `number` INTEGER NOT NULL,
    `hotel_id` INTEGER NOT NULL,
    `date_arrival` DATE NOT NULL,
    `date_department` DATE NOT NULL,
    `status` TINYINT NOT NULL CHECK (`status` <= 1),
    #0 - waiting 1 - accept
    CONSTRAINT FK_booking_rooms FOREIGN KEY (`number`,`hotel_id`)
        REFERENCES `rooms` (`number`,`hotel_id`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;
#
CREATE TABLE `orders` (
                              `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                              `user_id` INTEGER UNSIGNED NOT NULL,
                              `hotel_id` INTEGER NOT NULL,
                              `number` INTEGER NOT NULL,
                              `date_arrival` DATE NOT NULL,
                              `date_department` DATE NOT NULL,
                              `status` TINYINT NOT NULL CHECK (`status` <= 3),
                              #0 - сancel 1-waiting 2-accept 3-execute
                              CONSTRAINT FK_orders_rooms FOREIGN KEY (`hotel_id`,`number`)
                                  REFERENCES `rooms` (`hotel_id`,`number`)
                                  ON UPDATE CASCADE
                                  ON DELETE RESTRICT,
                            CONSTRAINT FK_orders_users FOREIGN KEY (`user_id`)
                                  REFERENCES `users` (`id`)
                                  ON UPDATE CASCADE
                                  ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;
#
# CREATE TABLE `orders_users` (
#           `order_id` INTEGER NOT NULL,
#           `user_id` INTEGER UNSIGNED NOT NULL,
#       CONSTRAINT FK_orders_guests_orders FOREIGN KEY (`order_id`)
#               REFERENCES `orders` (`id`)
#               ON UPDATE CASCADE
#               ON DELETE RESTRICT,
#       CONSTRAINT FK_orders_users_users FOREIGN KEY (`user_id`)
#               REFERENCES `users` (`id`)
#               ON UPDATE CASCADE
#               ON DELETE RESTRICT
#   ) ENGINE=INNODB DEFAULT CHARACTER SET utf8;
