
package findsimilardoctor;
import java.util.*;
/**
 * Now the project can find similar doctors based on their speciality and location
 * 
 * Things can be improved:
 * 
 * 1. we can find similar doctors by the conditions that they can handle 
 * 
 * 2. similarity computation can also take patients' review into consideration.
 * 
 * 2. instead of using zipcode as one metric to compute doctors' similarity
 * we can compute the real distance between their locations to define the similarity
 * 
 */
public class FindSimilarDoctor {
    
    /* class to store a pair of doctor and a similarity score
    */
    class DoctorScorePair{
        Doctor doctor;
        int score;
        
        public DoctorScorePair(Doctor doctor, int score) {
            this.doctor = doctor;
            this.score = score;
        } 
    }
    
    /**
    * This method find similar return the top k similar doctos to the given doctor
    * The algorithm uses a heap to find the top k similar doctors
    * @param target the given doctor we want to find similar doctor to it 
    * @param doctors the list of all the doctors
    * @param k the number of similar doctors that requird
    * @return List<Doctor> This returns a list similar doctor by given target doctor
    */
    public List<Doctor> FindSimilar (Doctor target, List<Doctor> doctors, int k) {
        //min heap to store the most similar K doctors
        PriorityQueue<DoctorScorePair> pq = new PriorityQueue<>(k, new Comparator<DoctorScorePair>(){
            public int compare(DoctorScorePair ds1, DoctorScorePair ds2) {
                if (ds1.score == ds2.score)
                    return ds1.doctor.getReviewScore() - ds2.doctor.getReviewScore();
                return ds1.score - ds2.score;
            }
        });
        
        for(Doctor doctor: doctors) {
            if(!doctor.getId().equals(target.getId())) {
                int similarityScore = target.computeSimilarity(doctor);
                if(pq.size() < k ) {
                    pq.offer(new DoctorScorePair(doctor, similarityScore));
                }
                else if(similarityScore > pq.peek().score 
                    || (similarityScore == pq.peek().score && doctor.getReviewScore() > pq.peek().doctor.getReviewScore())) {
                    pq.poll();
                    pq.offer(new DoctorScorePair(doctor, similarityScore));
                }
            }
        }
        
        List<Doctor> result = new LinkedList<>();
        while(!pq.isEmpty()) {
            DoctorScorePair pair = pq.poll();
            result.add(0, pair.doctor);
        }
        return result;
    }
    
    private void unitTest(Doctor d1, FindSimilarDoctor find, List<Doctor> allDoctors, int k) {
        System.out.println("Test Case:-----------------------------------------Start");
        System.out.println(d1.toString());
        System.out.println("Similar doctors to " + d1.getName());
        List<Doctor> case1 = find.FindSimilar(d1, allDoctors, k);
        for(Doctor doctor: case1) {
            System.out.println(doctor.toString());
        }
        System.out.println("Test Case:-------------------------------------------End");
    }
    
    public static void main(String[] args) {
        List<Doctor> allDoctors = new ArrayList<>();
        Doctor d1 = new Doctor("1", "Shawn Tran", "Cardiology", "94122", "1612 19th Ave, 94122, San Francisco", "123-234-2341", 3);
        Doctor d2 = new Doctor("2", "Bradley Puder", "Family Medicine", "94132", "1600 Holloway, 94132, San Francisco", "123-245-1231", 4);
        Doctor d3 = new Doctor("3", "Selina Leon", "Cardiology", "94404", "78 Rock Harbor Lane, 94404, Foster City", "123-345-0911", 4);
        Doctor d4 = new Doctor("4", "Jim Green", "General Surgery", "94407", "32 Santie Dr, 94407, San Mateo", "123-395-0192", 2);
        Doctor d5 = new Doctor("5", "Emily Yoon", "Obstetrics", "94403", "783 Skyline Blvd, 94003, Daly City", "324-345-1217", 4);
        Doctor d6 = new Doctor("6", "Stuard Noan", "Oncology", "94005", "537 Bright Street, 94005, South San Francisco", "123-235-3456", 1);
        Doctor d7 = new Doctor("7", "Swash Mule", "Cardiology", "95348", "439 Samuel Way, 95328, Merced", "492-245-2984", 3);
        Doctor d8 = new Doctor("8", "Noah Wooka", "Family Medicine", "95322", "123 Some St, 95322, Atwater", "453-345-2341", 3);
        allDoctors.add(d1);
        allDoctors.add(d2);
        allDoctors.add(d3);
        allDoctors.add(d4);
        allDoctors.add(d5);
        allDoctors.add(d6);
        allDoctors.add(d7);
        allDoctors.add(d8);
        
        FindSimilarDoctor find = new FindSimilarDoctor();
        
        //Similarity Score Computation Test 1
        System.out.println("Similarity Score between "+ d1.getName() + " and " + d3.getName() + " is " + d1.computeSimilarity(d3));
        System.out.println("Similarity Score between "+ d1.getName() + " and " + d7.getName() + " is " + d1.computeSimilarity(d7));
        System.out.println("Similarity Score between "+ d4.getName() + " and " + d5.getName() + " is " + d4.computeSimilarity(d5));
        System.out.println("Similarity Score between "+ d4.getName() + " and " + d8.getName() + " is " + d4.computeSimilarity(d8));
        System.out.println();
        
        //Ranking Unit Test 1:
        //Purpose: Test when there are two candidates with same similarity score
        // the result should rank the one that wiht higher review score
        find.unitTest(d1, find, allDoctors, 3);
        
        //Ranking Unit Test 2:
        //Purpose: Test when there is no candidates with same speciality
        // the result should return the closed doctors in terms of location
        find.unitTest(d4, find, allDoctors, 3);
        
        //Ranking Unit Test 3:
        //Purpose: Test the behavior of the program when k is greater the the total number of doctors
        find.unitTest(d1, find, allDoctors, 100);
    }   
}
