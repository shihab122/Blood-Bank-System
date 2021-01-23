package com.codewithshihab.server.service;

import com.codewithshihab.server.exception.ExecutionFailureException;
import com.codewithshihab.server.models.*;
import com.codewithshihab.server.models.Error;
import com.codewithshihab.server.repository.BloodRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BloodRequestService {
    private final BloodRequestRepository bloodRequestRepository;
    private final UserService userService;

    public BloodRequestService(BloodRequestRepository bloodRequestRepository, UserService userService) {
        this.bloodRequestRepository = bloodRequestRepository;
        this.userService = userService;
    }

    public List<BloodRequest> findAll() {
        return bloodRequestRepository.findAll();
    }

    public BloodRequest findById(String bloodRequestId) throws ExecutionFailureException {
        Optional<BloodRequest> optionalBloodRequest = bloodRequestRepository.findById(bloodRequestId);
        if (!optionalBloodRequest.isPresent()) {
            throw new ExecutionFailureException(
                    new Error(400, "bloodRequestId", "Invalid Blood Request", "Blood request does not exist.")
            );
        }
        return optionalBloodRequest.get();
    }

    public BloodRequest create(BloodRequest bloodRequest, String accessToken) throws ExecutionFailureException {
        bloodRequest.setRequestBy(userService.getUserFromAccessToken(accessToken));
        validateBloodRequest(bloodRequest);

        BloodRequest newBloodRequest = new BloodRequest();
        newBloodRequest.setRequestBy(bloodRequest.getRequestBy());
        newBloodRequest.setAlternateMobileNumber(bloodRequest.getAlternateMobileNumber());
        newBloodRequest.setRelationshipWithPatient(bloodRequest.getRelationshipWithPatient());
        newBloodRequest.setRequestedBloodGroup(bloodRequest.getRequestedBloodGroup());
        newBloodRequest.setTimeFrame(bloodRequest.getTimeFrame());

        return bloodRequestRepository.insert(newBloodRequest);
    }

    public BloodRequest addBloodDonationDate(String bloodRequestId, LocalDateTime donationDate, String accessToken) throws ExecutionFailureException {
        String username = userService.getUsernameFromAccessToken(accessToken);
        if (userService.getUserTypeFromUsername(username).equals(UserType.ADMIN)
                && donationDate != null
                && !donationDate.isBefore(LocalDateTime.now())
        ) {
            BloodRequest bloodRequest = findById(bloodRequestId);
            bloodRequest.setDonationTime(donationDate);
            sendEmail(bloodRequest.getRequestedBloodGroup());
            return bloodRequestRepository.save(bloodRequest);
        }
        else if (!userService.getUserTypeFromUsername(username).equals(UserType.ADMIN)) {
            throw new ExecutionFailureException(
                    new Error(400, "userType", "Not Eligible", "User is not eligible for set blood donation date.")
            );
        }
        else{
            throw new ExecutionFailureException(
                    new Error(400, "donationDate", "Invalid Donation Date", "Valid donation date needed for blood request.")
            );
        }
    }

    public BloodRequest addInterestedDonorInList(String bloodRequestId, String accessToken) throws ExecutionFailureException {
        BloodRequest bloodRequest = findById(bloodRequestId);
        User interestedDonor = userService.getUserFromAccessToken(accessToken);

        checkEligibilityForBloodDonation(interestedDonor, bloodRequest);

        if (bloodRequest.getInterestedDonorList() == null) bloodRequest.setInterestedDonorList(new ArrayList<>());
        bloodRequest.getInterestedDonorList().add(interestedDonor);

        return bloodRequestRepository.save(bloodRequest);
    }

    public BloodRequest addDonorInformationAfterGivingBlood(String donorUsername, String bloodRequestId, String accessToken) throws ExecutionFailureException {
        if (!userService.getUserTypeFromUsername(userService.getUsernameFromAccessToken(accessToken)).equals(UserType.ADMIN)) {
            throw new ExecutionFailureException(
                    new Error(400, "userType", "Not Eligible", "User is not eligible for set blood donation date.")
            );
        }
        BloodRequest bloodRequest = findById(bloodRequestId);

        if (bloodRequest.getDonationInformationList() == null) bloodRequest.setDonationInformationList(new ArrayList<>());
        bloodRequest.getDonationInformationList().add( new DonationInformation(userService.getByUsername(donorUsername), LocalDateTime.now()));

        return bloodRequestRepository.save(bloodRequest);
    }

    public BloodRequest addFeedback(Feedback feedback, String bloodRequestId, String accessToken) throws ExecutionFailureException {
        BloodRequest bloodRequest = findById(bloodRequestId);
        feedback.setGivenBy(userService.getUserFromAccessToken(accessToken));

        validateFeedback(feedback);

        if (bloodRequest.getFeedbackList() == null) {
            bloodRequest.setFeedbackList(new ArrayList<>());
            bloodRequest.getFeedbackList().add(feedback);
        }

        return bloodRequestRepository.save(bloodRequest);
    }

    public void sendEmail(String bloodGroup) {
        List<User> userList = userService.findAll();
        for (User user: userList) {
            if (user.getBloodGroup().equals(bloodGroup)) {
                //TODO Email Sent Code Write Here
            }
        }
    }

    public void checkEligibilityForBloodDonation(User user, BloodRequest bloodRequest) throws ExecutionFailureException {
        if (!user.getBloodGroup().equals(bloodRequest.getRequestedBloodGroup())){
            throw new ExecutionFailureException(
                    new Error(400, "", "Not Eligible", "Blood group not matched.")
            );
        }
        else if (!user.getAvailableFrom().isBefore(LocalDateTime.now())){
            throw new ExecutionFailureException(
                    new Error(400, "", "Not Eligible", "You won't be able donate blood before " + user.getAvailableFrom().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            );
        }
        else if (bloodRequest.getInterestedDonorList() != null && bloodRequest.getInterestedDonorList().size() >= 3) {
            throw new ExecutionFailureException(
                    new Error(400, "", "Not Eligible", "Already added maximum number of interested people.")
            );
        }
        else if (bloodRequest.getInterestedDonorList() != null) {
            for (User donor: bloodRequest.getInterestedDonorList()) {
                if (donor.getUsername().equals(user.getUsername())) {
                    throw new ExecutionFailureException(
                            new Error(400, "", "Already Added", "Already added as a donor for this blood request.")
                    );
                }
            }
        }
    }

    private void validateBloodRequest(BloodRequest bloodRequest) throws ExecutionFailureException {
        if (bloodRequest.getRequestBy() == null) {
            throw new ExecutionFailureException(
                    new Error(400, "username", "Invalid Requester", "Blood requester must be a valid user.")
            );
        }
        else if (bloodRequest.getRelationshipWithPatient().isEmpty()) {
            throw new ExecutionFailureException(
                    new Error(400, "relationshipWithPatient", "Invalid Relation Status", "Valid relationship status needed for blood request.")
            );
        }
        else if (bloodRequest.getAlternateMobileNumber().isEmpty()) {
            throw new ExecutionFailureException(
                    new Error(400, "alternateMobileNumber", "Invalid Alternate Mobile Number", "Valid alternate mobile number needed for blood request.")
            );
        }
        else if (bloodRequest.getTimeFrame() == null
                || (bloodRequest.getTimeFrame().getFrom().isBefore(LocalDateTime.now()) && bloodRequest.getTimeFrame().getTo().isAfter(LocalDateTime.now()))) {
            throw new ExecutionFailureException(
                    new Error(400, "timeFrame", "Invalid Time Frame", "Valid time frame needed for blood request.")
            );
        }
        else if (bloodRequest.getRequestedBloodGroup().isEmpty() || !bloodRequest.getRequestedBloodGroup().matches("^(A|B|AB|O)[+-]$")) {
            throw new ExecutionFailureException(
                    new Error(400, "requestedBloodGroup", "Invalid Requested Blood Group", "Valid time frame needed for blood request.")
            );
        }
    }

    private void validateFeedback(Feedback feedback) throws ExecutionFailureException {
        if (feedback.getDescription().isEmpty() || feedback.getDescription().length() < 2) {
            throw new ExecutionFailureException(
                    new Error(400, "description", "Invalid Feedback", "Feedback must be 2 characters long.")
            );
        }
        else if (feedback.getGivenBy() == null) {
            throw new ExecutionFailureException(
                    new Error(400, "givenBy", "Invalid User", "User does not exist.")
            );
        }
    }

    public void deleteRequest(String id) {
        bloodRequestRepository.deleteById(id);
    }
}
