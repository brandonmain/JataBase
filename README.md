<!DOCTYPE html>
	<html>
		<head>
			<meta charset="UTF-8">
			<title>README</title>
		</head>
		<style>
			body{
				background-color: #FDFDFD;
				margin-left: 20%;
				margin-right: 20%;
			}

			h3{
				font-weight: 550;
			}

			code{
				background-color: #eee;
			}
			img{
				display: block;
				text-align: center;
				border-radius: 5px;
				opacity: .9;
				margin: 15px auto 15px auto;
				height: 25%;
				width: 75%;
				min-width: 500px;
			}
	
		</style>
		<body>
			<p>JataBase version 1.0</p>
			<p>Written by: Brandon Main</p>
			<p>Date: February 20, 2019</p>
			<h2 style="text-align: center">README</h2>

			<h3>How to compile and run:</h3>
			<p>Download and unzip the .tar.gz file in your working directory. You should then contain the following files such as those listed in the screenshot below:</p>
			<img id="ls" src="img/ls.png">
			<p>First we must compile the java files by issueing the <code>make</code> command:</p>
			<img id="make" src="img/make.png">
			<p>After compiling we can now run the JataBase program using a file as a command line arguement or by inputing commands by issueing the <code>java JataBase &lt;FILE | NULL&gt;</code> commands at the command line.</p>
			<img id="start" src="img/start.png">

			<h3>Database Organization</h3>
			<p>This program is designed to treat each individual database as a directory. Therefore, whenever a new database is created, a new directory in the current working directory is created with the name that is given. Databases within databases could even be created if desired. Below is an example of how to create a database.</p>

			<p>Create a database:</p>
			<code>JataBase~# CREATE DATABASE &lt;DATABASE-NAME&gt;</code>
			<img id="create" src="img/CREATE_DB.png">
			<p>Listing the contents of your working directory will then show the databases newly created folder:</p>
			<img id="proof" src="img/proof.png">

			<h3>Table Management</h3>
			<p>With databases treated as directories, each of the tables in the databases are treated as individual files within the databases directory. In order to create a table there must already be a database created, and that database must be specified as the database you are using by issueing the <code>JataBase~# USE &lt;DATABASE-NAME&gt;</code> command. Below is an example of how to perform the operation of creating a table.</p>

			<p>First a database must be used:</p>
			<code>JataBase~# USE &lt;DATABASE-NAME&gt;</code>
			<img id="using" src="img/using_db.png">

			<p>Then a table can be created:</p>
			<code>JataBase~# CREATE TABLE &lt;TABLE-NAME&gt; (VALUES_1, ..., VALUE_N)</code>
			<img id="table" src="img/table.png">

			<p>A tables contents can be viewed by issueing the <code>SELECT</code> command:</p>
			<code>JataBase~# SELECT * FROM &lt;TABLE-NAME&gt;</code>
			<img id="select" src="img/select.png">

			<p>A tables contents can also be altered by issueing the <code>ALTER</code> command:</p>
			<img id="alter" src="img/alter.png">

			<h3>How does it all work?</h3>
			<p>To implement the required functionalities of this project I found it best to write the program in java, hence the name JataBase (Java + Database). By reading in a file of strings consisting of commands for the database or reading from the command line, the program can easily parse the commands and decide what to do next. Based on what is input from file or from the command line the program then calls the corresponding function to handle the command. If the command is not recognized the program will print an error to the terminal and continue running.</p>
		
		</body>
	</html>