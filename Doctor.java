
package findsimilardoctor;

/**
 *
 * @author xuejing
 * xuejing@mail.sfsu.edu
 */
public class Doctor {

    private String name;
    private String id;
    private String speciality;
    private String zipcode;
    private String address;
    private int reviewScore;
    private String phoneNumber;
    
    public Doctor(String id, String name, String speciality, String zipcode,
            String address, String phoneNumber, int reviewScore) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.zipcode = zipcode;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.reviewScore = reviewScore;
    } 
    
    /**
    * This method computes a similarity between a given doctor and this doctor
    * and the similarity is computed by a few metrics.
    * The similairity computing metrics:
    * 1. Same speciality: 30 points
    * 2. Same zipcode: 20 points
    * 3. Frist 4 digits of the zipcode matches: 15 points
    * 3. Frist 3 digits of the zipcode matches: 10 points
    * 3. Frist 2 digits of the zipcode matches: 5 points
    * @param doctor 
    * @return int This returns a similarity Score
    */
    public int computeSimilarity(Doctor doctor) {
        int similarityScore = 0;
        
        if(doctor.getSpeciality() != null && this.speciality.equals(doctor.getSpeciality()))
            similarityScore += 30;
        
        if(doctor.getZipcode() != null) {
            if(this.zipcode.equals(doctor.getZipcode())) {
                similarityScore += 20;
            }
            else if(this.zipcode.substring(0, 4).equals(doctor.getZipcode().substring(0,4))) {
                similarityScore += 15;
            }
            else if(this.zipcode.substring(0, 3).equals(doctor.getZipcode().substring(0,3))) {
                similarityScore += 10;
            }
            else if(this.zipcode.substring(0, 2).equals(doctor.getZipcode().substring(0,2))) {
                similarityScore += 10;
            }
        }
        return similarityScore;
    }
    
    public String toString() {
        String str = "";
        str += this.name +"     " + this.reviewScore + " Star" + '\n';
        str += this.speciality + '\n';
        str += this.address + '\n';
        return str;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }    
            
    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(int reviewScore) {
        this.reviewScore = reviewScore;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    
    
}
