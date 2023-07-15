package com.cst438.domain;

public class StudentDTO
{
   public int student_id;
   public String name;
   public String email;
   public int statusCode;
   public String status;
   
 //default constructor
   public StudentDTO() {
      this.student_id = 0;
      this.name=null;
      this.email=null;  
      this.statusCode = 0;
      this.status = null;
   }
   
 //working constructor
   public StudentDTO(String name, String email, int student_id, int statusCode, String status) {
      this.student_id = 0;
      this.name = name;
      this.email = email;
      this.statusCode = statusCode;
      this.status = status;
   }
   
  
   @Override
   public String toString() {
      return "StudentDTO [student_id=" + student_id + ", name=" + name + ", email=" + email + ", statusCode="
            + statusCode + ", status=" + status + "]";
   }
   
   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      StudentDTO other = (StudentDTO) obj;
      if (name == null) {
         if (other.name != null)
            return false;
      } else if (!name.equals(other.name))
         return false;
      if (email == null) {
         if (other.email != null)
            return false;
      } else if (!email.equals(other.email))
         return false;
      if (status == null) {
         if (other.status != null)
            return false;
      } else if (!status.equals(other.status))
         return false;
      if (student_id != other.student_id)
         return false;
      if (statusCode != other.statusCode)
         return false;
      return true;
   }
   
}
