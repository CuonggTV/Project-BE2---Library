create table fine (
                      id int primary key,
                      amount float not null,
                      deadline date not null,
                      customerID int not null,
                      loanID int not null,
                      FOREIGN KEY (customerID) REFERENCES customer(id),
                      FOREIGN KEY (loanID) REFERENCES loan(id)
);

create table loan (
                      id int primary key,
                      bookID int not null,
                      loanDate date not null,
                      returnDate date not null,
                      FOREIGN KEY (bookID) REFERENCES book(id)
);


create table book (
                      id int primary key,
                      bookName varchar(255) not null,
                      author varchar(255) not null,
                      borrewedFee float not null,
                      copiesOwned int not null
);

create table category (
                          id int primary key,
                          bookID int not null,
                          name varchar(255) not null,
                          FOREIGN KEY (bookID) REFERENCES book(id)
);

create table bookcategories (
                                id int primary key,
                                bookID int not null,
                                categoryID int not null,
                                FOREIGN KEY (bookID) REFERENCES book(id),
                                FOREIGN KEY (categoryID) REFERENCES category(id)
);

create table reservation (
                             id int primary key,
                             customerID int not null,
                             bookID int not null,
                             dateCreated date not null,
                             status varchar(20) not null,
                             FOREIGN KEY (bookID) REFERENCES book(id),
                             FOREIGN KEY (customerID) REFERENCES customer(id)
);