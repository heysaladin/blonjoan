-- ****************** SqlDBM: MySQL ******************;
-- ***************************************************;

DROP TABLE `Product`;


DROP TABLE `GroceryHistory`;


DROP TABLE `Grocery`;


DROP TABLE `ProductCategory`;


DROP TABLE `GroceryLog`;


DROP TABLE `GroceryCategory`;



-- ************************************** `ProductCategory`

CREATE TABLE `ProductCategory`
(
 `ProductCategoryID`     INT NOT NULL ,
 `ProductCategoryParent` INT NOT NULL ,
 `ProductCategoryName`   VARCHAR(45) NOT NULL ,

PRIMARY KEY (`ProductCategoryID`)
);





-- ************************************** `GroceryLog`

CREATE TABLE `GroceryLog`
(
 `GroceryLogID` INT NOT NULL ,
 `Date`         TIMESTAMP NOT NULL ,
 `RealPrice`    DOUBLE NOT NULL ,

PRIMARY KEY (`GroceryLogID`)
);





-- ************************************** `GroceryCategory`

CREATE TABLE `GroceryCategory`
(
 `GroceryCategoryID`     INT NOT NULL ,
 `GroceryCategoryParent` INT NOT NULL ,
 `GroceryCategoryName`   VARCHAR(45) NOT NULL ,

PRIMARY KEY (`GroceryCategoryID`)
);





-- ************************************** `Product`

CREATE TABLE `Product`
(
 `ProductID`         INT NOT NULL ,
 `Name`              VARCHAR(255) NOT NULL ,
 `ItemPrice`         DOUBLE NOT NULL ,
 `ProductCategoryID` INT NOT NULL ,

PRIMARY KEY (`ProductID`),
KEY `fkIdx_62` (`ProductCategoryID`),
CONSTRAINT `FK_62` FOREIGN KEY `fkIdx_62` (`ProductCategoryID`) REFERENCES `ProductCategory` (`ProductCategoryID`)
);





-- ************************************** `GroceryHistory`

CREATE TABLE `GroceryHistory`
(
 `GroceryHistoryID`  INT NOT NULL ,
 `Name`              VARCHAR(255) NOT NULL ,
 `Price`             DOUBLE NOT NULL ,
 `Quantity`          FLOAT NOT NULL ,
 `PriceSubTotal`     DOUBLE NOT NULL ,
 `RealPrice`         DOUBLE NOT NULL ,
 `GroceryCategoryID` INT NOT NULL ,
 `GroceryLogID`      INT NOT NULL ,

PRIMARY KEY (`GroceryHistoryID`),
KEY `fkIdx_42` (`GroceryLogID`),
CONSTRAINT `FK_42` FOREIGN KEY `fkIdx_42` (`GroceryLogID`) REFERENCES `GroceryLog` (`GroceryLogID`),
KEY `fkIdx_46` (`GroceryCategoryID`),
CONSTRAINT `FK_46` FOREIGN KEY `fkIdx_46` (`GroceryCategoryID`) REFERENCES `GroceryCategory` (`GroceryCategoryID`)
);





-- ************************************** `Grocery`

CREATE TABLE `Grocery`
(
 `GroceryID`         INT NOT NULL ,
 `Name`              VARCHAR(255) NOT NULL ,
 `Price`             DOUBLE NOT NULL ,
 `Quantity`          FLOAT NOT NULL ,
 `PriceSubTotal`     DOUBLE NOT NULL ,
 `RealPrice`         DOUBLE NOT NULL ,
 `GroceryCategoryID` INT NOT NULL ,

PRIMARY KEY (`GroceryID`),
KEY `fkIdx_18` (`GroceryCategoryID`),
CONSTRAINT `FK_18` FOREIGN KEY `fkIdx_18` (`GroceryCategoryID`) REFERENCES `GroceryCategory` (`GroceryCategoryID`)
);




