CREATE DATABASE `website_hotels_db` DEFAULT CHARACTER SET utf8;

create user 'website_hotels_app'@localhost
        identified by 'website_hotels_password';

GRANT SELECT,INSERT,UPDATE,DELETE
    ON `website_hotels_db`.*
    TO website_hotels_app@localhost;

#GRANT SELECT,INSERT,UPDATE,DELETE
#ON `website_hotels_db`.*
#TO website_hotels_user@'%';
#IDENTIFIED BY 'website_hotels_password';