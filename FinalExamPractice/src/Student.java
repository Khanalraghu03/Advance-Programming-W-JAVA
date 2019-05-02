import java.util.Objects;

public class Student implements Comparable<Student>
    {
        public String fname;
        public String lname;
        public String bannerID;

        public Student (String first, String last, String bannerID)
            {
                this.fname = first;
                this.lname = last;
                this.bannerID = bannerID;
            }

        public String getFname ()
            {
                return fname;
            }

        public void setFname (String fname)
            {
                this.fname = fname;
            }

        public String getLname ()
            {
                return lname;
            }

        public void setLname (String lname)
            {
                this.lname = lname;
            }

        public String getBannerID ()
            {
                return bannerID;
            }

        public void setBannerID (String bannerID)
            {
                this.bannerID = bannerID;
            }

        @Override
        public String toString ()
            {
//                return "Student{" +
//                        "fname='" + fname + '\'' +
//                        ", lname='" + lname + '\'' +
//                        ", bannerID='" + bannerID + '\'' +
//                        '}';
                return "[" + bannerID + ": " + lname + ", " + fname + "]";
            }

        @Override
        public int hashCode()
            {
                return Math.abs((fname.hashCode() + lname.hashCode()) % 17);
            }


//        public int hashCodee() {
////            return Objects.hash(fname, lname, bannerID);
//            return Math.abs((fname.hashCode() + lname.hashCode()) % 17);
//        }



        //Equals method is based on BannerID
        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            Student otherStudent = (Student) obj;
            return bannerID.equalsIgnoreCase(otherStudent.getBannerID());
        }




//        public boolean equalss(Object o) {
////            if (this == o) return true;
//
//            if (o == null ) return false;     //|| getClass() != o.getClass()
//            Student student = (Student) o;
//
////            return Objects.equals(fname, student.fname) &&
////                    Objects.equals(lname, student.lname) &&
////                    Objects.equals(bannerID, student.bannerID);
//            return bannerID.equalsIgnoreCase(((Student) o).bannerID);
//        }


//        @Override
//        public int compareTo (Student compareStudent)
//            {
//                int studentBannerID = Integer.parseInt(bannerID);
//                int compareStudentBannerID = Integer.parseInt(compareStudent.getBannerID());
//                return studentBannerID - compareStudentBannerID;
//            }

        @Override
        public int compareTo(Student o) {
            int studentBannerID = Integer.parseInt(bannerID);

            int compareStudentBannerID = Integer.parseInt(o.getBannerID());

            //returns positive # is studentBannerID is greater than compareStudentBannerID
            // "" negative # if opposite is true
            // "" 0 if they
            return studentBannerID - compareStudentBannerID;
        }

    }
