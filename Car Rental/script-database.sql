USE [master]
GO
/****** Object:  Database [Assignment3_HongDaiDuong]    Script Date: 3/7/2021 8:29:23 PM ******/
CREATE DATABASE [Assignment3_HongDaiDuong]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Assignment3_HongDaiDuong', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Assignment3_HongDaiDuong.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Assignment3_HongDaiDuong_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Assignment3_HongDaiDuong_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Assignment3_HongDaiDuong].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET ARITHABORT OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET  MULTI_USER 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET QUERY_STORE = OFF
GO
USE [Assignment3_HongDaiDuong]
GO
/****** Object:  Table [dbo].[tblAccount]    Script Date: 3/7/2021 8:29:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblAccount](
	[email] [nvarchar](50) NOT NULL,
	[password] [nvarchar](50) NULL,
	[phone] [nvarchar](50) NULL,
	[name] [nvarchar](50) NULL,
	[address] [nvarchar](50) NULL,
	[createDate] [nvarchar](50) NULL,
	[isActive] [bit] NULL,
	[code] [nchar](10) NULL,
	[isAdmin] [bit] NULL,
 CONSTRAINT [PK_tblAccount] PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblCar]    Script Date: 3/7/2021 8:29:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblCar](
	[carID] [int] NOT NULL,
	[name] [nvarchar](50) NULL,
	[color] [nvarchar](50) NULL,
	[year] [int] NULL,
	[img] [nvarchar](max) NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[rating] [int] NULL,
	[categoryID] [int] NULL,
 CONSTRAINT [PK_tblCar] PRIMARY KEY CLUSTERED 
(
	[carID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblCategory]    Script Date: 3/7/2021 8:29:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblCategory](
	[categoryID] [int] NOT NULL,
	[name] [nvarchar](50) NULL,
 CONSTRAINT [PK_tblCategory] PRIMARY KEY CLUSTERED 
(
	[categoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblRent]    Script Date: 3/7/2021 8:29:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblRent](
	[id] [int] NOT NULL,
	[createDate] [nvarchar](50) NULL,
	[totalQuantity] [int] NULL,
	[totalPrice] [float] NULL,
	[payment] [nvarchar](50) NULL,
	[isDelete] [bit] NULL,
	[email] [nvarchar](50) NULL,
	[isSale] [bit] NULL,
	[code] [nchar](10) NULL,
 CONSTRAINT [PK_tblRent] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblRentDetail]    Script Date: 3/7/2021 8:29:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblRentDetail](
	[rentID] [int] NOT NULL,
	[carID] [int] NOT NULL,
	[carName] [nvarchar](50) NULL,
	[unitPrice] [float] NULL,
	[quantity] [int] NULL,
	[totalPrice] [float] NULL,
	[rentDate] [nvarchar](50) NOT NULL,
	[returnDate] [nvarchar](50) NOT NULL,
	[isFeedBack] [bit] NULL,
	[rating] [int] NULL,
	[isReturn] [bit] NULL,
 CONSTRAINT [PK_tblRentDetail] PRIMARY KEY CLUSTERED 
(
	[rentID] ASC,
	[carID] ASC,
	[rentDate] ASC,
	[returnDate] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblSale]    Script Date: 3/7/2021 8:29:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblSale](
	[code] [nchar](10) NOT NULL,
	[expireDate] [nvarchar](50) NULL,
	[percentSale] [int] NULL,
 CONSTRAINT [PK_tblSale] PRIMARY KEY CLUSTERED 
(
	[code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[tblAccount] ([email], [password], [phone], [name], [address], [createDate], [isActive], [code], [isAdmin]) VALUES (N'duonghdse140501@fpt.edu.vn', N'123456', N'0909123456', N'Đại Gia', N'Quận 12', N'07/03/2021 19:56:22', 0, N'595408    ', 0)
INSERT [dbo].[tblAccount] ([email], [password], [phone], [name], [address], [createDate], [isActive], [code], [isAdmin]) VALUES (N'duongkodoiqua@gmail.com', N'123456', N'0123456789', N'Đại Dương', N'Hóc Môn', N'28/02/2021 11:21:43', 1, N'919524    ', 0)
INSERT [dbo].[tblCar] ([carID], [name], [color], [year], [img], [price], [quantity], [rating], [categoryID]) VALUES (1, N'Toyota Gold', N'gold', 2000, N'https://drawtodrive.com/uploads/337/vladislav-23arts-shapovalenko-toyota-rav4-gold.jpeg', 3000, 10, 10, 2)
INSERT [dbo].[tblCar] ([carID], [name], [color], [year], [img], [price], [quantity], [rating], [categoryID]) VALUES (2, N'Toyota Swing', N'blue', 2002, N'https://images.dealer.com/autodata/us/color/2020/USD00TOS112B0/8W9.jpg', 4000, 10, 10, 2)
INSERT [dbo].[tblCar] ([carID], [name], [color], [year], [img], [price], [quantity], [rating], [categoryID]) VALUES (3, N'Vinfast Crossroll', N'red', 2010, N'https://vinfastchevroletnewway.com.vn/wp-content/uploads/2019/09/sa-red_7-169.jpg', 5000, 5, 10, 3)
INSERT [dbo].[tblCar] ([carID], [name], [color], [year], [img], [price], [quantity], [rating], [categoryID]) VALUES (4, N'Vinfast Attitude', N'black', 2020, N'https://vinfastchevroletnewway.com.vn/wp-content/uploads/2020/09/vinfast-president-anh-dai-dien-640x360.jpg', 2400, 10, 10, 3)
INSERT [dbo].[tblCar] ([carID], [name], [color], [year], [img], [price], [quantity], [rating], [categoryID]) VALUES (5, N'Hurricane', N'green', 2018, N'https://www.gbwestbend.com/inventoryphotos/5814/1fa6p8cf3k5117586/sp/1.jpg?height=400', 4500, 10, 10, 1)
INSERT [dbo].[tblCar] ([carID], [name], [color], [year], [img], [price], [quantity], [rating], [categoryID]) VALUES (6, N'Canon', N'white', 2017, N'https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/2020-toyota-supra-white-1552941613.jpg', 2000, 10, 10, 1)
INSERT [dbo].[tblCategory] ([categoryID], [name]) VALUES (1, N'Compact')
INSERT [dbo].[tblCategory] ([categoryID], [name]) VALUES (2, N'Minivan')
INSERT [dbo].[tblCategory] ([categoryID], [name]) VALUES (3, N'Truck')
INSERT [dbo].[tblRent] ([id], [createDate], [totalQuantity], [totalPrice], [payment], [isDelete], [email], [isSale], [code]) VALUES (1, N'2/3/2021', 8, 30000, N'cash', 0, N'duongkodoiqua@gmail.com', 0, N'0         ')
INSERT [dbo].[tblRent] ([id], [createDate], [totalQuantity], [totalPrice], [payment], [isDelete], [email], [isSale], [code]) VALUES (2, N'2/3/2021', 6, 22000, N'cash', 0, N'duongkodoiqua@gmail.com', 0, N'0         ')
INSERT [dbo].[tblRent] ([id], [createDate], [totalQuantity], [totalPrice], [payment], [isDelete], [email], [isSale], [code]) VALUES (3, N'06/03/2021', 11, 33000, N'Cash', 1, N'duongkodoiqua@gmail.com', 0, N'0         ')
INSERT [dbo].[tblRent] ([id], [createDate], [totalQuantity], [totalPrice], [payment], [isDelete], [email], [isSale], [code]) VALUES (4, N'06/03/2021', 5, 23750, N'Bank', 0, N'duongkodoiqua@gmail.com', 1, N'221232    ')
INSERT [dbo].[tblRentDetail] ([rentID], [carID], [carName], [unitPrice], [quantity], [totalPrice], [rentDate], [returnDate], [isFeedBack], [rating], [isReturn]) VALUES (1, 1, N'Toyota Gold', 3000, 5, 15000, N'5/3/2021', N'7/3/2021', 0, 0, 0)
INSERT [dbo].[tblRentDetail] ([rentID], [carID], [carName], [unitPrice], [quantity], [totalPrice], [rentDate], [returnDate], [isFeedBack], [rating], [isReturn]) VALUES (1, 3, N'Vinfast Crossroll', 5000, 3, 15000, N'6/3/2021', N'8/3/2021', 0, 0, 0)
INSERT [dbo].[tblRentDetail] ([rentID], [carID], [carName], [unitPrice], [quantity], [totalPrice], [rentDate], [returnDate], [isFeedBack], [rating], [isReturn]) VALUES (2, 1, N'Toyota Gold', 3000, 2, 6000, N'15/3/2021', N'20/3/2021', 0, 0, 0)
INSERT [dbo].[tblRentDetail] ([rentID], [carID], [carName], [unitPrice], [quantity], [totalPrice], [rentDate], [returnDate], [isFeedBack], [rating], [isReturn]) VALUES (2, 2, N'Toyota Swing', 4000, 4, 16000, N'10/3/2021', N'15/3/2021', 0, 0, 0)
INSERT [dbo].[tblRentDetail] ([rentID], [carID], [carName], [unitPrice], [quantity], [totalPrice], [rentDate], [returnDate], [isFeedBack], [rating], [isReturn]) VALUES (3, 1, N'Toyota Gold', 3000, 6, 18000, N'8/3/2021', N'10/3/2021', 0, 0, 1)
INSERT [dbo].[tblRentDetail] ([rentID], [carID], [carName], [unitPrice], [quantity], [totalPrice], [rentDate], [returnDate], [isFeedBack], [rating], [isReturn]) VALUES (3, 1, N'Toyota Gold', 3000, 5, 15000, N'8/3/2021', N'15/3/2021', 0, 0, 1)
INSERT [dbo].[tblRentDetail] ([rentID], [carID], [carName], [unitPrice], [quantity], [totalPrice], [rentDate], [returnDate], [isFeedBack], [rating], [isReturn]) VALUES (4, 3, N'Vinfast Crossroll', 5000, 5, 25000, N'11/3/2021', N'15/3/2021', 0, 0, 0)
INSERT [dbo].[tblSale] ([code], [expireDate], [percentSale]) VALUES (N'0         ', N'xxx', 0)
INSERT [dbo].[tblSale] ([code], [expireDate], [percentSale]) VALUES (N'112233    ', N'8/3/2021', 10)
INSERT [dbo].[tblSale] ([code], [expireDate], [percentSale]) VALUES (N'123321    ', N'10/3/2021', 5)
INSERT [dbo].[tblSale] ([code], [expireDate], [percentSale]) VALUES (N'221232    ', N'15/3/2021', 5)
ALTER TABLE [dbo].[tblCar]  WITH CHECK ADD  CONSTRAINT [FK_tblCar_tblCategory] FOREIGN KEY([categoryID])
REFERENCES [dbo].[tblCategory] ([categoryID])
GO
ALTER TABLE [dbo].[tblCar] CHECK CONSTRAINT [FK_tblCar_tblCategory]
GO
ALTER TABLE [dbo].[tblRent]  WITH CHECK ADD  CONSTRAINT [FK_tblRent_tblAccount] FOREIGN KEY([email])
REFERENCES [dbo].[tblAccount] ([email])
GO
ALTER TABLE [dbo].[tblRent] CHECK CONSTRAINT [FK_tblRent_tblAccount]
GO
ALTER TABLE [dbo].[tblRent]  WITH CHECK ADD  CONSTRAINT [FK_tblRent_tblSale] FOREIGN KEY([code])
REFERENCES [dbo].[tblSale] ([code])
GO
ALTER TABLE [dbo].[tblRent] CHECK CONSTRAINT [FK_tblRent_tblSale]
GO
ALTER TABLE [dbo].[tblRentDetail]  WITH CHECK ADD  CONSTRAINT [FK_tblRentDetail_tblCar] FOREIGN KEY([carID])
REFERENCES [dbo].[tblCar] ([carID])
GO
ALTER TABLE [dbo].[tblRentDetail] CHECK CONSTRAINT [FK_tblRentDetail_tblCar]
GO
ALTER TABLE [dbo].[tblRentDetail]  WITH CHECK ADD  CONSTRAINT [FK_tblRentDetail_tblRent] FOREIGN KEY([rentID])
REFERENCES [dbo].[tblRent] ([id])
GO
ALTER TABLE [dbo].[tblRentDetail] CHECK CONSTRAINT [FK_tblRentDetail_tblRent]
GO
USE [master]
GO
ALTER DATABASE [Assignment3_HongDaiDuong] SET  READ_WRITE 
GO
