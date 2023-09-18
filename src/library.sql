USE libraryDB;

create table Customer (
	id int primary key AUTO_INCREMENT,
    username varchar(50),
    password varchar(50),
    firstname varchar(20),
    middlename varchar(50),
    lastname varchar(20),
    phonenumber char(10)
);

create table Book (
	id int primary key AUTO_INCREMENT,
    bookName varchar(255) not null,
    author varchar(255) not null,
    borrowedFee float not null,
    copiesOwned int not null
);

create table Status (
	id int primary key AUTO_INCREMENT,
    is_done bit,
    description varchar(255)
);

create table Loan (
	id int primary key AUTO_INCREMENT,
    bookID int not null,
    customerID int not null,
    loanDate date not null,
    returnDate date not null,
    FOREIGN KEY (bookID) REFERENCES book(id),
    FOREIGN KEY (customerID) REFERENCES customer(id)
);

create table Fine (
	id int primary key AUTO_INCREMENT,
    amount float not null,
    deadline date not null,
    customerID int not null,
    loanID int not null,
    FOREIGN KEY (customerID) REFERENCES customer(id),
    FOREIGN KEY (loanID) REFERENCES loan(id)
);

create table Category (
	id int primary key AUTO_INCREMENT,
    name varchar(255) not null
);

create table BookCategories (
	id int primary key AUTO_INCREMENT,
    bookID int not null,
    categoryID int not null,
    FOREIGN KEY (bookID) REFERENCES book(id),
    FOREIGN KEY (categoryID) REFERENCES category(id)
);

create table Reservation (
	id int primary key AUTO_INCREMENT,
    customerID int not null,
    bookID int not null,
    statusID int not null,
    dateCreated date not null,
    FOREIGN KEY (bookID) REFERENCES book(id),
    FOREIGN KEY (customerID) REFERENCES customer(id),
	FOREIGN KEY (statusID) REFERENCES status(id)
);