/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.resources;


import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author root
 */
@Slf4j
public class ErrorCodes {
    
    
    public static final int SUCCESSFUL = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    
    // INVALID_TOKEN_AGE, INVALID_CUSTOMER_CODE,MAX_NUMBER_OF_API_CLIENTS_EXCEEDED
    
    public static final int BAD_REQUEST = 400;
    public static final int PARTNER_VALIDATION_ERROR = 602;
    public static final int INVALID_CONTROL_CODE = 603;
    public static final int INVALID_ACTOR = 604;
    public static final int INVALID_OBJECT = 605;
    public static final int INVALID_PRIZE_TYPE = 606;
    public static final int EXPIRED_SUBSCRIPTION = 607;
    public static final int UHURU_SUBCRIPTION_ERROR = 608;
    public static final int INVALID_BILLER_ITEM = 609;
    public static final int INACTIVE_SUBSCRIPTION = 610;
    public static final int WHEEL_CONFIG_ERROR = 611;
    public static final int SPIN_ENTRY_ERROR = 612;
    public static final int MAX_ALLOWED_PLAY_COUNT_EXCEEDED = 613;
    public static final int UN_SUBCRIPTION_ERROR = 614;
    public static final int INVALID_PRODUCT_ID = 615;
    public static final int INVALID_SENDER_ID = 616;
    public static final int INVALID_MESSAGE = 617;
    public static final int INVALID_SERVICEID = 618;
    public static final int SUBCRIPTION_ERROR = 619;
    public static final int INVALID_PAGE_ID = 620;
    public static final int INVALID_PAGE_NAME = 621;
    public static final int INVALID_WIN_TYPE = 622;
    public static final int INVALID_WHEEL_LABEL = 623;
    public static final int NOT_SUBSCRIBED = 624;
    public static final int ALREADY_SUBSCRIBED = 625;
    public static final int INVALID_SPIN_VALUE = 626;
    public static final int INVALID_AUTO_OPTION = 627;
    public static final int INVALID_OF_DAYS = 628;
    public static final int INVALID_PLAN = 629;
    public static final int NO_ANSWER_SETUP_FOR_QUESTION = 630;
    public static final int QUESTION_ALREADY_ANSWERED = 631;
    public static final int MAX_NUMBER_OF_ANSWER_OPTIONS_FOR_QUESTION = 632;
    public static final int NOT_YOUR_QUESTION = 633;
    public static final int INVALID_ANSWER_TAG = 634;
    public static final int QUESTION_CAN_HAVE_ONLY_ONE_ANSWER = 635;
    public static final int INVALID_ANSWER = 636;
    public static final int INVALID_ANSWER_INDICATOR = 637;
    public static final int INVALID_ANSWER_SELECTED = 638;
    public static final int INVALID_HOW_TO_PLAY_STEP_LABEL = 639;
    public static final int INVALID_HOW_TO_PLAY_STEP_MSG = 640;
    public static final int INVALID_PLAY_STEP = 641;
    public static final int INVALID_PLAY_STEP_LABEL = 642;
    public static final int INVALID_HEADER_INFO = 643;
    public static final int INVALID_HEADER_INFO_DATA = 644;
    public static final int INVALID_CTA_ONE_LABEL = 645;
    public static final int INVALID_CTA_TWO_LABEL = 646;
    public static final int INVALID_PARAM_VALUE = 647;
    public static final int INVALID_PARAM_NAME = 648;
    public static final int INVALID_CLIENT = 649;
    public static final int MAX_NUMBER_OF_API_CLIENTS_EXCEEDED = 650;
    public static final int INVALID_CUSTOMER_CODE = 651;
    public static final int INVALID_TOKEN_AGE = 652;
    public static final int INVALID_STATUS = 653;
    public static final int INVALID_CLIENT_NAME = 654;
    public static final int INVALID_COUNTRY = 655; 
    public static final int INVALID_STATE = 656;
    public static final int INVALID_GENDER = 657;
    public static final int INVALID_PAYMENT_BENEFICIARY = 658; 
    public static final int INVALID_IP_ADDRESS = 659;
    public static final int OBJECT_CONFLICT = 660;
    public static final int INVALID_OPERATOR = 661;
    public static final int PENDING_AUTHORIZATION = 662;
    public static final int INVALID_ORIGINATOR_NARRATION = 663;
    public static final int INVALID_EASYPAY_TOKEN = 664;
    public static final int INVALID_SRC_ACCOUNT = 665;
    public static final int INVALID_EASYPAY_TRANID = 666;
    public static final int INVALID_CHANNEL_CODE = 667;
    public static final int SUCCESSFULLY_REVERSED = 668;
    public static final int INVALID_ROLE_NAME = 669;
    public static final int INVALID_ROLE_DESC = 670;
    public static final int INVALID_UPPER_LIMIT = 671;
    public static final int INVALID_LIMIT = 672;
   // public static final int INVALID_DESCRIPTION = 673;
    public static final int PAYMENT_ERROR = 674;
    public static final int INVALID_PAYMENT_TYPE = 675;
    public static final int INVALID_SEARCHKEY_OR_VALUE = 676;
    public static final int INVALID_IMAGE = 677;
    public static final int INVALID_DISPUTE_ACTION = 678;
    public static final int INVALID_DISPUTE_ACTION_DATA = 679;
    public static final int INVALID_IMAGE_FORMAT = 680;
    public static final int INVALID_CATEGORY = 681;
    public static final int INVALID_DISPUTE_COMMENT = 682;
    public static final int INVALID_DISPUTE_SUBJECT = 683;
    public static final int DISPUTE_ACTION_ONGOING = 684;
    public static final int INVALID_USER = 685;
    public static final int INVALID_ROLE = 686;
    public static final int INVALID_BILLER_BILLER_NAME = 687;
    public static final int INVALID_QUESTION = 688;
    public static final int INVALID_CUSTOMER_ID = 689;
    public static final int INVALID_BILLER_CATEGORY_NAME = 690;
    public static final int INVALID_BILLER_PAYTV_PROVIDER = 691;
    public static final int INVALID_BILLER_CATEGORY = 692;
    public static final int INVALID_BILLER_AIRTIME_NETWORK = 693;
    public static final int AUTO_REVERSAL_SUCCESSFUL = 694;
    public static final int AUTO_REVERSAL_FAILED = 695;
    public static final int EXPIRED_OTP = 696;
    public static final int INVALID_TRANS_TYPE = 697;
    public static final int USER_PROFILE_EXISTS = 698;
    public static final int INVALID_IMEI = 699;
    public static final int INVALID_PIN = 700;
    public static final int INVALID_ACCOUNT = 701;
    public static final int ISSUER_OR_SWITCH_NOT_AVAILABLE = 702;
    public static final int SYSTEM_ERROR = 703;
    public static final int DATABASE_ERROR = 704;
    public static final int INVALID_SESSION_ID = 705;
    public static final int INVALID_CHEQUE_LEAVES_COUNT = 706;
    public static final int INVALID_AMOUNT = 707;
    public static final int INVALID_NARRATION = 708;
    public static final int INVALID_DEST_BANKCODE = 709;
    public static final int PIN_ALREADY_GENERATED = 710;
    public static final int EXPIRED_JWT = 711;
    public static final int DECRYPTION_ERROR = 712;
    public static final int FORMAT_ERROR = 713;
    public static final int COMM_LINK = 714;
    public static final int DUPLICATE_TRANSACTION = 715;
    public static final int INVALID_REQUEST_ID = 716;
    public static final int JWT_SIGNATURE_EXCEPTION = 717;
    public static final int INVALID_DEST_ACCOUNT = 718;
    public static final int INVALID_FIRSNAME = 719;
    public static final int INVALID_LASTNAME = 720;
    public static final int INVALID_EMAIL = 721;
    public static final int INVALID_MSISDN = 722;
    public static final int INVALID_TOKEN = 723;
    public static final int USER_ALREADY_EXIST = 724;
    public static final int INVALID_CONFIRM_PIN = 725;
    public static final int PIN_MISMATCH = 726;
    public static final int TRANSACTION_FORBIDDEN = 727;
    public static final int INVALID_USERNAME = 728;
    //public static final int INVALID_USERNAME_OR_PASSWORD = 729;
    public static final int SECURITY_VIOLATION = 730;
    public static final int MOBILE_ACCOUNT_MIX_MATCH = 731;
    public static final int NO_PRINCIPAL_ACCOUNT = 732;
    public static final int IO_EXCEPTION = 733; 
    public static final int INVALID_NAME_ENQUIRY_REF = 734;
    public static final int INVALID_SRC_KYC = 735;
    public static final int INVALID_DEST_KYC = 736;
    public static final int INVALID_SRC_ACCOUNTNAME = 737;
    public static final int INVALID_DEST_ACCOUNTNAME = 738;
    
    public static final int INVALID_SRC_BVN = 739;
    public static final int INVALID_DEST_BVN = 740;
    public static final int SERVICE_PROVIDER_UNREACHABLE = 741;
    public static final int SERVICE_PROCESSOR_UNREACHABLE = 742;
    public static final int INVALID_VERIFY_PIN = 743;
    public static final int PIN_TOO_SIMPLE = 744;
    public static final int FORBIDDEN_ACTION = 745;
    public static final int INVALID_TRANSACTION = 746;
    public static final int LIMIT_EXCEEDED = 747;
    public static final int INVALID_USER_JWT = 748;
    //to map the the error mapper on the auth engine
    public static final int INVALID_CONFIRM_PASSWORD = 854;
    public static final int PASSWORD_MISMATCH = 750;
    public static final int INVALID_PASSWORD = 751;
    
    public static final int PASSWORD_TOO_SIMPLE = 752;
    public static final int INVALID_NEW_PASSWORD = 753;
    public static  final int INVALID_BENEFICIARY_NAME = 754;
    public static  final int INVALID_BENEFICIARY_BANK_CODE= 755;
    public static  final int INVALID_BENEFICIARY_BANK_NAME= 756;
    public static  final int INVALID_BENEFICIARY_ACCOUNT_NUMBER = 757;
    public static  final int INVALID_BENEFICIARY_ALIAS= 758;
    public static  final int INVALID_BENEFICIARY = 759;
    public static  final int NO_RECORD_FOUND = 760;
    public static  final int MULTIPLE_FAILED_LOGINS = 761;
    
    
    public static final int UNKNOWN_USER = 901;
    
    public static final int INVALID_USER_OR_PASSWORD = 850;
    //public static final int INVALID_CONFIRM_PASSWORD = 854;
    public static final int INSUFFICIENT_FUNDS = 859;
    public static final int _2FA_VERIFICATION_REQUIRED = 860;
    public static final int INVALID_DEVICE_FINGERPRINT = 861;
    public static final int DEVICE_SWAP_DETECTED = 862;
    public static final int INVALID_START_DATE = 863;
    public static final int INVALID_END_DATE = 864;
    public static final int DATE_DISPARITY = 865;
    public static final int INVALID_PAGE_ID_OR_FETCH_SIZE = 866;
    public static final int INVALID_ENTITY = 867;
    public static final int INVALID_DESCRIPTION = 868;
    //Sorry you do not have enough funds for this transaction, please fund your account and try again
    
    
    public static String doErrorDesc(int code, int prodOrDev) {
        System.out.println("prodOrDev = " + prodOrDev+" code : "+code);
        String resp = "";
        try 
        {
          if(prodOrDev == 0)
          {
            switch(code)
            {
                case 200:
                resp = "You have been successfully Login";
                break;
                case 201:
                resp = "Your Mobile Profile has been successfully created. Login to Change your transaction pin";
                break;
                case 202:
                resp = "input successfully accepted";
                break;
                case BAD_REQUEST:
                resp = "BAD_REQUEST  [Error code 400]";
                break;
                 case PARTNER_VALIDATION_ERROR:
                resp = "PARTNER_VALIDATION_ERROR  [Error code 602]";
                break;
                case INVALID_CONTROL_CODE:
                resp = "INVALID_CONTROL_CODE  [Error code 603]";
                break;
                
                case INVALID_ACTOR:
                resp = "INVALID_ACTOR  [Error code 604]";
                break;
                case INVALID_OBJECT:
                resp = "INVALID_OBJECT  [Error code 605]";
                break;
                case INVALID_PRIZE_TYPE:
                resp = "INVALID_PRIZE_TYPE  [Error code 606]";
                break;
                case EXPIRED_SUBSCRIPTION:
                resp = "EXPIRED_SUBSCRIPTION  [Error code 607]";
                break;
                case UHURU_SUBCRIPTION_ERROR:
                resp = "EXPIRED_SUBSCRIPTION  [Error code 608]";
                break;
                case INVALID_BILLER_ITEM:
                resp = "INVALID_BILLER_ITEM  [Error code 609]";
                break;
                case INACTIVE_SUBSCRIPTION:
                resp = "INACTIVE_SUBSCRIPTION kindy resubscribe and try again [Error code 610]";
                break;
                case WHEEL_CONFIG_ERROR:
                resp = "WHEEL_CONFIG_ERROR [Error code 611]";
                break;
                case SPIN_ENTRY_ERROR:
                resp = "SPIN_ENTRY_ERROR [Error code 612]";
                break;
                 case MAX_ALLOWED_PLAY_COUNT_EXCEEDED:
                resp = "MAX_ALLOWED_PLAY_COUNT_EXCEEDED [Error code 613]";
                break;
                case UN_SUBCRIPTION_ERROR:
                resp = "UN_SUBCRIPTION_ERROR [Error code 614]";
                break;
                case INVALID_PRODUCT_ID:
                resp = "INVALID_PRODUCT_ID [Error code 615]";
                break;
                case INVALID_SENDER_ID:
                resp = "INVALID_SENDER_ID [Error code 616]";
                break;
                case INVALID_MESSAGE:
                resp = "INVALID_MESSAGE [Error code 617]";
                break;
                case INVALID_SERVICEID:
                resp = "INVALID_SERVICEID [Error code 618]";
                break;
                case SUBCRIPTION_ERROR:
                resp = "SUBCRIPTION_ERROR [Error code 619]";
                break;
                case INVALID_PAGE_ID:
                resp = "INVALID_PAGE_ID [Error code 620]";
                break;
                case INVALID_PAGE_NAME:
                resp = "INVALID_PAGE_NAME [Error code 621]";
                break;
                 case INVALID_WIN_TYPE:
                resp = "INVALID_WIN_TYPE [Error code 622]";
                break;
                case INVALID_WHEEL_LABEL:
                resp = "INVALID_WHEEL_LABEL [Error code 623]";
                break;
                case NOT_SUBSCRIBED:
                resp = "NOT_SUBSCRIBED [Error code 624]";
                break;
                case ALREADY_SUBSCRIBED:
                resp = "ALREADY_SUBSCRIBED [Error code 625]";
                break;
                case INVALID_SPIN_VALUE:
                resp = "INVALID_SPIN_VALUE [Error code 626]";
                break;
                case INVALID_AUTO_OPTION:
                resp = "INVALID_AUTO_OPTION [Error code 627]";
                break;
                case INVALID_OF_DAYS:
                resp = "INVALID_OF_DAYS [Error code 628]";
                break;
                case INVALID_PLAN:
                resp = "INVALID_PLAN [Error code 629]";
                break;
                case NO_ANSWER_SETUP_FOR_QUESTION:
                resp = "NO_ANSWER_SETUP_FOR_QUESTION [Error code 630]";
                break;
                case QUESTION_ALREADY_ANSWERED:
                resp = "QUESTION_ALREADY_ANSWERED [Error code 631]";
                break;
                
                case MAX_NUMBER_OF_ANSWER_OPTIONS_FOR_QUESTION:
                resp = "MAX_NUMBER_OF_ANSWER_OPTIONS_FOR_QUESTION [Error code 632]";
                break;
                
                case NOT_YOUR_QUESTION:
                resp = "NOT_YOUR_QUESTION [Error code 633]";
                break;
                case INVALID_ANSWER_TAG:
                resp = "INVALID_ANSWER_TAG [Error code 634]";
                break;
                 case QUESTION_CAN_HAVE_ONLY_ONE_ANSWER:
                resp = "QUESTION_CAN_HAVE_ONLY_ONE_ANSWER [Error code 635]";
                break;
                case INVALID_ANSWER:
                resp = "INVALID_ANSWER [Error code 636]";
                break;
                case INVALID_ANSWER_INDICATOR:
                resp = "INVALID_ANSWER_OPTION [Error code 637]";
                break;
                case INVALID_ANSWER_SELECTED:
                resp = "INVALID_ANSWER_SELECTED [Error code 638]";
                break;
                 case INVALID_HOW_TO_PLAY_STEP_LABEL:
                resp = "INVALID_HOW_TO_PLAY_STEP_LABEL [Error code 639]";
                break;
                case INVALID_HOW_TO_PLAY_STEP_MSG:
                resp = "INVALID_HOW_TO_PLAY_STEP_MSG [Error code 640]";
                break;
                case INVALID_PLAY_STEP:
                resp = "INVALID_PLAY_STEP [Error code 641]";
                break;
                case INVALID_PLAY_STEP_LABEL:
                resp = "INVALID_PLAY_STEP_LABEL [Error code 642]";
                break;
                case INVALID_HEADER_INFO:
                resp = "INVALID_HEADER_INFO [Error code 643]";
                break;
                case INVALID_HEADER_INFO_DATA:
                resp = "INVALID_HEADER_INFO_DATA [Error code 644]";
                break;
                case INVALID_CTA_ONE_LABEL:
                resp = "INVALID_STATUS [Error code 645]";
                break;
                case INVALID_CTA_TWO_LABEL:
                resp = "INVALID_CTA_TWO_LABEL [Error code 646]";
                break;
                case INVALID_PARAM_VALUE:
                resp = "INVALID_PARAM_VALUE [Error code 647]";
                break;
                case INVALID_PARAM_NAME:
                resp = "INVALID_PARAM_NAME [Error code 648]";
                break;
                case INVALID_STATUS:
                resp = "INVALID_STATUS [Error code 653]";
                break;
                
                case MAX_NUMBER_OF_API_CLIENTS_EXCEEDED:
                resp = "MAX_NUMBER_OF_API_CLIENTS_EXCEEDED [Error code 650]";
                break;
                
                case INVALID_CUSTOMER_CODE:
                resp = "INVALID_CUSTOMER_CODE [Error code 651]";
                break;
                
                case INVALID_TOKEN_AGE:
                resp = "INVALID_TOKEN_AGE [Error code 652]";
                break;
                case INVALID_CLIENT:
                resp = "INVALID_CLIENT [Error code 649]";
                break;
                case INVALID_CLIENT_NAME:
                resp = "INVALID_CLIENT_NAME [Error code 654]";
                break;
                case INVALID_COUNTRY:
                resp = "INVALID_COUNTRY [Error code 655]";
                break;
                 case INVALID_STATE:
                resp = "INVALID_STATE [Error code 656]";
                break;
                case INVALID_GENDER:
                resp = "INVALID_GENDER [Error code 657]";
                break;
                case INVALID_PAYMENT_BENEFICIARY:
                resp = "INVALID_PAYMENT_BENEFICIARY [Error code 658]";
                break;
                case INVALID_IP_ADDRESS:
                resp = "INVALID_IP_ADDRESS [Error code 659]";
                break;
                case OBJECT_CONFLICT:
                resp = "OBJECT_CONFLICT [Error code 660]";
                break;
                case INVALID_OPERATOR:
                resp = "INVALID_OPERATOR [Error code 661]";
                break;
                case PENDING_AUTHORIZATION:
                resp = "PENDING_AUTHORIZATION [Error code 662]";
                break;
                case INVALID_ORIGINATOR_NARRATION:
                resp = "INVALID_ORIGINATOR_NARRATION [Error code 663]";
                break;
                case INVALID_EASYPAY_TOKEN:
                resp = "INVALID_EASYPAY_TOKEN [Error code 664]";
                break;   
                case INVALID_SRC_ACCOUNT: //666
                resp = "INVALID_SRC_ACCOUNT [Error code 665]";
                break;
                case INVALID_CHANNEL_CODE: //666
                resp = "Invalid Channel code [Error code 666]";
                break;
              
                case INVALID_EASYPAY_TRANID:
                resp = "Invalid easy pay tranid should be 30 characters fixed length with the clienr code of 6 digits the 12 digit date componen in YYMMDDHHMMSS + random 12 digits [Error code 666]";
                break;
                case 668:
                resp = "Successfully Reversed [Error code 668]";
                break;
                case 669:
                resp = "Invalid role name [Error code 669]";
                break;
                
                case 670:
                resp = "Invalid role description [Error code 670]";
                break;
                case 671:
                resp = "Invalid upper limit [Error code 671]";
                break;
                
                case 672:
                resp = "Invalid limit lower cannot be more that upper and they cannot be equal [Error code 672]";
                break;
                
                case 673:
                resp = "Invalid Description [Error code 673]";
                break;
                case 674:
                resp = "Bill Payment Error  [Error code 674]";
                break;
                case 675:
                resp = "Invalid payment type  [Error code 675]";
                break;
                case 676:
                resp = "Invalid search key or value  [Error code 676]";
                break;
                case 677:
                resp = "Invalid profile image  [Error code 677]";
                break;
                case 678:
                resp = "Invalid dispute action  [Error code 678]";
                break;
                case 679:
                resp = "Invalid dispute action data [Error code 679]";
                break;
                case INVALID_IMAGE_FORMAT:
                resp = "INVALID_IMAGE_FORMAT [Error code 680]";
                break;
                case INVALID_CATEGORY:
                resp = "INVALID_CATEGORY [Error code 681]";
                break;
                case 682:
                resp = "Invalid dispute subject [Error code 682]";
                break;
                case 683:
                resp = "Invalid dispute subject [Error code 683]";
                break;
                case 684:
                resp = "Dispute action ongoing [Error code 684]";
                break;
                case 685:
                resp = "Invalid user [Error code 685]";
                break;
                case 686:
                resp = "Invalid role [Error code 686]";
                break;
                case 687:
                resp = "Invalid Biller Name [Error code 687]";
                break;
                case 688:
                resp = "INVALID_QUESTION [Error code 688]";
                break;
                case 689:
                resp = "Invalid customer Id [Error code 689 tag name customerId]";
                break;
                case 690:
                resp = "Invalid Biller Category Name [Error code 690]";
                break;
                
                case 691:
                resp = "Invalid Biller Pay TV  provider [Error code 691]";
                break;
               
                case 692:
                resp = "Invalid Biller Category [Error code 692]";
                break;
                case 693:
                resp = "Invalid Biller Airtime Network [Error code 693]";
                break;
                case 694:
                resp = "Auto reversal successful [Error code 694]";
                break;
                 case 695:
                resp = "Auto reversal failed, kindly contact administrator or raise a dispute [Error code 695]";
                break;
                case 696:
                resp = "your OTP has expired [Error code 696]";
                break;
                case 697:
                resp = "Invalid Transaction Type [Error code 697]";
                break;
                case 698:
                resp = "Your Mobile Profile already exists, Log in to your profile [Error code 698]";
                break;
                case 699:
                resp = "INVALID_IMEI [Error code 699]";
                break;
                case 700:
                resp = "You have entered an Invalid Transaction PIN, kindly check and try again [Error code 700]";
                break;
                case 701:
                resp = "You have entered an Invalid Account Number, kindly check and try again.[Error code 701 tag name srcAccount]";
                break;
                case 702:
                resp = "Sorry, you cannot log in at this time. kindly try again later. [Error code 702]";
                break;
                case 703:
                resp = "Sorry, we have encountered an error, kindly contact the administrator [Error code 703]";
                break;
                case 704:
                resp = "Sorry, a database error has occurred kindly contact the administrator.[Error code 704]";
                break;
                case INVALID_SESSION_ID:
                resp = "INVALID_SESSION_ID, kindly check and try again [Error code 705]";
                break;
                case 706:
                resp = "The Cheque is not valid for this account, kindly check and try again.[Error code 706]";
                break;
                case 707:
                resp = "You have entered an Invalid Amount, kindly check and try again [Error code 707]";
                break;
                case 708:
                resp = "You have entered an Invalid Narration, kindly check and try again.[Error code 708]";
                break;
                case 709:
                resp = "You have entered an Invalid Beneficiary Bank, kindly contact the Beneficiary Bank and try again later [Error code 709 tag name destinationInstitutionCode, expected length is 6 digits]";
                break;
                case 710:
                resp = "You have entered an Invalid Transaction Pin, kindly check and try again.[Error code 710]";
                break;
                case 711:
                resp = "Sorry, your token has expired, kindly contact admin.[Error code 711]";
                break;
                /*
                case 711:
                resp = "Sorry, you cannot log in at this time. Please try again later.[Error code 711]";
                break;
                */
                case 712:
                resp = "Sorry, decryption error, kindly contact admin [Error code 712]";
                break;
                case 713:
                resp = "Sorry, you cannot log in at this time. Please try again later [Error code 713]";
                break;
                case 714:
                resp = "Sorry, we encountered an error communicating with the processing service. Kindly report to the administrator.[Error code 714]";
                break;
                case 715:
                resp = "Duplicate entry detected, kindly  verify entries and try again.[Error code 715]";
                break;
                case 716:
                resp = "Sorry, you have entered an invalid requestId. Please correct and try again. [Error code 716]";
                break;
                case 717:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 717]";
                break;
                case 718:
                resp = "You have entered an Invalid Beneficiary Account number, please check and try again. [Error code 718]";
                break;
                case 719:
                resp = "You have entered an Invalid First name, please check and try again. [Error code 719]";
                break;
                case 720:
                resp = "You have entered an Invalid Last name, please check and try again. [Error code 720]";
                break;
                case 721:
                resp = "You have entered an Invalid email address, please check and try again. [Error code 721 tag name emailAddress]";
                break;
                case INVALID_MSISDN:
                resp = "Sorry, you have provided an invalid mobile no .[Error code 722 json tag name mobileNo]";
                break;
                case 723:
                resp = "Sorry, you have entered an invalid token, please check and try again. [Error code 723]";
                break;
                case 724:
                resp = "Your User Profile already exist. Log in to your profile. [Error code 724]";
                break;
                case 725:
                resp = "The confirm pin you enter does not match, please check and try again. [Error code 725]";
                break;
                case 726:
                resp = "You have entered an Invalid transaction pin, please check and try again.[Error code 726]";
                break;
                case 727:
                resp = "Transaction on this account is not allowed at this time, please try again later. [Error code 727]";
                break;
                case 728:
                resp = "You have entered an Invalid username, please check and try again. [Error code 728]";
                break;
                case 729:
                resp = "You have entered an Invalid login detail, please enter the correct login details [Error code 729]";
                break;
                case 850: // mapped to auth service resp
                resp = "You have entered an Invalid login detail, please enter the correct login details.[Error code 850]";
                break;
                case 730:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 730]";
                break;
                case 731:
                resp = "The Mobile number does not match the registered number on the account. Please contact the bank for the registered phone number and try again. [Error code 731]";
                break;
                case 732:
                resp = "There is no account attached to this number. Please contact the Bank and try again later [Error code 732]";
                break;
                case IO_EXCEPTION:
                resp = "IO_EXCEPTION, Please try again later. [Error code 733]";
                break;
                case 734:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 734]";
                break;
                case 735:
                resp = "Invalid Sender Kyc. [Error code 735]";
                break;
                case 736:
                resp = "Invalid beneficiary KYC.[Error code 736]";
                break;
                case 737:
                resp = "Invalid sender account name.Please correct and try again later [Error code 737]";
                break;
                case 738:
                resp = "Invalid beneficiary account name. Please correct and try again later [Error code 738]";
                break;
                case 739: 
                resp = "Invalid Sender BVN. Please try again later.[Error code 739]";
                break;
                case 740:
                resp = "Invalid Benficiary BVN. Please try again later. [Error code 740]";
                break;
                case 741:
                resp = "Sorry, service provider unreachable. Please try again later. [Error code 741]";
                break;
                
                case 742:
                resp = "Sorry, service processor unreachable. Please try again later.[Error code 742]";
                break;
                
                case 743: 
                resp = "The verify pin you enter does not match, please check and try again [Error code 743]";
                break;
                
                case 744:
                resp = "The pin you enter is too easy to guess, please use more difficult pin to guess [Error code 744]";
                break;
                
                case 745:
                resp = "Transaction on this account is not allowed at this time, please try again later. [Error code 745]";
                break;
                
                case 746:
                resp = "Transaction on this account is not allowed at this time, please contact the Bank [Error code 746]";
                break;
                
                case 747:
                resp = "Daily transaction limit has been exceeded on this account; transaction is not allowed at this time. [Error code 747]";
                break;
                
                case INVALID_USER_JWT:
                resp = "INVALID_USER_JWT  [Error code 748]";
                break;
                
                case 749:
                resp = "The confirm password you enter does not match, please check and try again [Error code 749 tag name verifyPassword]";
                break;
               case 750:
                resp = "The new password you entered does not match the password provided, please check and try again [Error code 750]";
                break;
               case 751:
                resp = "You have entered an Invalid Password, please check and try again [Error code 751]";
                break;
                case 752:
                resp = "The password you entered is too easy to guess, please use more difficult password to guess. [Error code 752]";
                break;
                case 753:
                resp = "You have entered an Invalid New Password, please check and try again. [Error code 753]";
                break;
                case 754:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 754]";
                break;
               case 755:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 755]";
                break;
                case 756:
                resp = "You have entered an Invalid Beneficiary Bank, please check and try again. [Error code 756]";
                break;
                case 757:
                resp = "You have entered an Invalid Beneficiary Account Number, please check and try again [Error code 757]";
                break;
                case 758:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later .[Error code 758]";
                break;
                case 759:
                resp = "You have entered an Invalid Beneficiary, please check and try again [Error code 759]";
                break;
                case 760:
                resp = "Sorry, there is no record for the requestid/request shared is not found on the systems. Please check and try again later. [Error code 760]";
                break;
                case 761:
                resp = "Multiple failed logins detected; your account has been locked. Please contact the Bank. [Error code 761]";
                break;
                 case INVALID_CONFIRM_PASSWORD:
                resp = "Confirm password does not match new password, or chosen passoword  [Error code 854]";
                break;
                case 859:
                resp = "Sorry you do not have enough funds for this transaction, please fund your account and try again [Error code 762]";
                break;
                
                
                case 860:
                resp = "This transaction requires an OTP verification, kindly verify the OTP sent to you,  to complete";
                break;
                case 861:
                resp = "Invalid Device Finger Print [Error code 861]";
                break;
                case 862:
                resp = " Device Swap detected, please verify OTP to continue [Error code 862]";
                break;
                
                case 863:
                resp = "Sorry you selected an invalid start date [Error code 863]";
                break;
                case 864:
                resp = "Sorry you do selected an invalid end date [Error code 864]";
                break;
                case 865:
                resp = "Sorry your start data must be a date before your end date [Error code 865]";
                break;
                case 866:
                resp = "Sorry you have entered an invalid page Id or page size [Error code 866]";
                break;
                case 867:
                resp = "Sorry you have selected an invalid entity [Error code 867]";
                break;
                case 868:
                resp = "Sorry you have entered an invalid description [Error code 868]";
                break;
                case 901:
                resp = "Sorry, you cannot log in at this time. Please try again later [Error code 901]";
                break;
                default:
                 resp= "Sorry, you cannot log in at this time. Please try again later. [Error code "+code+" ]";
                break;
            }
            
          }
          else
          {
             switch(code)
             {
                case 200:
                resp = "You have been successfully Login";
                break;
                case 201:
                resp = "Your Mobile Profile has been successfully created. Login to Change your transaction pin";
                break;
                case 202:
                resp = "input successfully accepted";
                break;
                
                case EXPIRED_SUBSCRIPTION:
                resp = "EXPIRED_SUBSCRIPTION  [Error code 607]";
                break;
                 case UHURU_SUBCRIPTION_ERROR:
                resp = "UHURU_SUBCRIPTION_ERROR  [Error code 608]";
                break;
                 case INVALID_BILLER_ITEM:
                resp = "INVALID_BILLER_ITEM  [Error code 609]";
                break;
                case INACTIVE_SUBSCRIPTION:
                resp = "INACTIVE_SUBSCRIPTION kindy resubscribe and try again[Error code 610]";
                break;
                case WHEEL_CONFIG_ERROR:
                resp = "WHEEL_CONFIG_ERROR [Error code 611]";
                break;
                case SPIN_ENTRY_ERROR:
                resp = "SPIN_ENTRY_ERROR [Error code 612]";
                break;
                 case MAX_ALLOWED_PLAY_COUNT_EXCEEDED:
                resp = "MAX_ALLOWED_PLAY_COUNT_EXCEEDED [Error code 613]";
                break;
                case UN_SUBCRIPTION_ERROR:
                resp = "UN_SUBCRIPTION_ERROR [Error code 614]";
                break;
                case INVALID_PRODUCT_ID:
                resp = "INVALID_PRODUCT_ID [Error code 615]";
                break;
                case INVALID_SENDER_ID:
                resp = "INVALID_SENDER_ID [Error code 616]";
                break;
                case INVALID_MESSAGE:
                resp = "INVALID_MESSAGE [Error code 617]";
                break;
                case INVALID_SERVICEID:
                resp = "INVALID_SERVICEID [Error code 618]";
                break;
                case SUBCRIPTION_ERROR:
                resp = "SUBCRIPTION_ERROR [Error code 619]";
                break;
                case INVALID_PAGE_ID:
                resp = "INVALID_PAGE_ID [Error code 620]";
                break;
                case INVALID_PAGE_NAME:
                resp = "INVALID_PAGE_NAME [Error code 621]";
                break;
                case INVALID_WIN_TYPE:
                resp = "INVALID_WIN_TYPE [Error code 622]";
                break;
                case INVALID_WHEEL_LABEL:
                resp = "INVALID_WHEEL_LABEL [Error code 623]";
                break;
                case NOT_SUBSCRIBED:
                resp = "NOT_SUBSCRIBED [Error code 624]";
                break;
                case ALREADY_SUBSCRIBED:
                resp = "ALREADY_SUBSCRIBED [Error code 625]";
                break;
                case INVALID_SPIN_VALUE:
                resp = "INVALID_SPIN_VALUE [Error code 626]";
                break;
                case INVALID_AUTO_OPTION:
                resp = "INVALID_AUTO_OPTION [Error code 627]";
                break;
                case INVALID_OF_DAYS:
                resp = "INVALID_OF_DAYS [Error code 628]";
                break;
                case INVALID_PLAN:
                resp = "INVALID_PLAN [Error code 629]";
                break;
                case NO_ANSWER_SETUP_FOR_QUESTION:
                resp = "NO_ANSWER_SETUP_FOR_QUESTION [Error code 630]";
                break;
                case QUESTION_ALREADY_ANSWERED:
                resp = "QUESTION_ALREADY_ANSWERED [Error code 631]";
                break;
                case MAX_NUMBER_OF_ANSWER_OPTIONS_FOR_QUESTION:
                resp = "MAX_NUMBER_OF_ANSWER_OPTIONS_FOR_QUESTION [Error code 632]";
                break;
                case NOT_YOUR_QUESTION:
                resp = "NOT_YOUR_QUESTION [Error code 633]";
                break;
                case INVALID_ANSWER_TAG:
                resp = "INVALID_ANSWER_TAG [Error code 634]";
                break;
                case QUESTION_CAN_HAVE_ONLY_ONE_ANSWER:
                resp = "QUESTION_CAN_HAVE_ONLY_ONE_ANSWER [Error code 635]";
                break;
                case INVALID_ANSWER:
                resp = "INVALID_ANSWER [Error code 636]";
                break;
                 case INVALID_ANSWER_INDICATOR:
                resp = "INVALID_ANSWER_INDICATOR [Error code 637]";
                break;
                case INVALID_ANSWER_SELECTED:
                resp = "INVALID_ANSWER_SELECTED tag name optionsTag [Error code 638]";
                break;
                 case INVALID_HOW_TO_PLAY_STEP_LABEL:
                resp = "INVALID_HOW_TO_PLAY_STEP_LABEL [Error code 639]";
                break;
                case INVALID_HOW_TO_PLAY_STEP_MSG:
                resp = "INVALID_HOW_TO_PLAY_STEP_MSG [Error code 640]";
                break;
                case INVALID_PLAY_STEP:
                resp = "INVALID_PLAY_STEP [Error code 641]";
                break;
                case INVALID_PLAY_STEP_LABEL:
                resp = "INVALID_PLAY_STEP_LABEL [Error code 642]";
                break;
                case INVALID_HEADER_INFO:
                resp = "INVALID_HEADER_INFO [Error code 643]";
                break;
                case INVALID_HEADER_INFO_DATA:
                resp = "INVALID_HEADER_INFO_DATA [Error code 644]";
                break;
                case INVALID_CTA_ONE_LABEL:
                resp = "INVALID_STATUS [Error code 645]";
                break;
                case INVALID_CTA_TWO_LABEL:
                resp = "INVALID_CTA_TWO_LABEL [Error code 646]";
                break;
                case INVALID_PARAM_VALUE:
                resp = "INVALID_PARAM_VALUE [Error code 647]";
                break;
                case INVALID_PARAM_NAME:
                resp = "INVALID_PARAM_NAME [Error code 648]";
                break;
               
                  case INVALID_CLIENT:
                resp = "INVALID_CLIENT [Error code 649]";
                break;
               
                case MAX_NUMBER_OF_API_CLIENTS_EXCEEDED:
                resp = "MAX_NUMBER_OF_API_CLIENTS_EXCEEDED [Error code 650]";
                break;
               
                case INVALID_CUSTOMER_CODE:
                resp = "INVALID_CUSTOMER_CODE [Error code 651]";
                break;
                
                case INVALID_TOKEN_AGE:
                resp = "INVALID_TOKEN_AGE [Error code 652]";
                break;
                
                case INVALID_STATUS:
                resp = "INVALID_STATUS [Error code 653]";
                break;
                case INVALID_CLIENT_NAME:
                resp = "INVALID_CLIENT_NAME [Error code 654]";
                break;
                case INVALID_COUNTRY:
                resp = "INVALID_COUNTRY [Error code 655]";
                break;
                 case INVALID_STATE:
                resp = "INVALID_STATE [Error code 656]";
                break;
                
                case INVALID_GENDER:
                resp = "INVALID_GENDER [Error code 657]";
                break;
                case INVALID_PAYMENT_BENEFICIARY:
                resp = "INVALID_PAYMENT_BENEFICIARY [Error code 658]";
                break;
                case INVALID_IP_ADDRESS:
                resp = "INVALID_IP_ADDRESS [Error code 659]";
                break;
                case OBJECT_CONFLICT:
                resp = "OBJECT_CONFLICT [Error code 660]";
                break;
                case INVALID_OPERATOR:
                resp = "INVALID_OPERATOR [Error code 661]";
                break;
                case PENDING_AUTHORIZATION:
                resp = "PENDING_AUTHORIZATION [Error code 662]";
                break;
                case INVALID_ORIGINATOR_NARRATION:
                resp = "INVALID_ORIGINATOR_NARRATION [Error code 663]";
                break;
                case INVALID_EASYPAY_TOKEN:
                resp = "INVALID_EASYPAY_TOKEN [Error code 664]";
                break;   
                case INVALID_SRC_ACCOUNT: //666
                resp = "INVALID_SRC_ACCOUNT [Error code 665]";
                break;
                case INVALID_CHANNEL_CODE: //666
                resp = "Invalid Channel code [Error code 666]";
                break;
                //case 667:
               // resp = "Auto Reversal Failure [Error code 667]";
                //break;
                
                
                case INVALID_EASYPAY_TRANID:
                resp = "Invalid easy pay tranid should be 30 characters fixed length with the clienr code of 6 digits the 12 digit date componen in YYMMDDHHMMSS + random 12 digits [Error code 666]";
                break;
                case 668:
                resp = "Successfully Reversed [Error code 668]";
                break;
                case 669:
                resp = "Invalid role name [Error code 669]";
                break;
                
                case 670:
                resp = "Invalid role description [Error code 670]";
                break;
                case 671:
                resp = "Invalid upper limit [Error code 671]";
                break;
                
                case 672:
                resp = "Invalid limit lower cannot be more that upper and they cannot be equal [Error code 672]";
                break;
                
                case 673:
                resp = "Invalid Description [Error code 673]";
                break;
                case 674:
                resp = "Bill Payment Error  [Error code 674]";
                break;
                case 675:
                resp = "Invalid payment type  [Error code 675]";
                break;
                case 676:
                resp = "Invalid search key or value  [Error code 676]";
                break;
                case 677:
                resp = "Invalid profile image  [Error code 677]";
                break;
                case 678:
                resp = "Invalid dispute action  [Error code 678]";
                break;
                case 679:
                resp = "Invalid dispute action data [Error code 679]";
                break;
                case INVALID_IMAGE_FORMAT:
                resp = "INVALID_IMAGE_FORMAT [Error code 680]";
                
                break;
                case 681:
                resp = "INVALID_CATEGORY [Error code 681]";
                break;
                case 682:
                resp = "Invalid dispute subject [Error code 682]";
                break;
                case 683:
                resp = "Invalid dispute subject [Error code 683]";
                break;
                case 684:
                resp = "Dispute action ongoing [Error code 684]";
                break;
                case 685:
                resp = "Invalid user [Error code 685]";
                break;
                case 686:
                resp = "Invalid role [Error code 686]";
                break;
                case 687:
                resp = "Invalid Biller Name [Error code 687]";
                break;
                case 688:
                resp = "INVALID_QUESTION [Error code 688]";
                break;
                case 689:
                resp = "Invalid customer Id [Error code 689 tag name customerId]";
                break;
                case 690:
                resp = "Invalid Biller Category Name [Error code 690]";
                break;
                
                case 691:
                resp = "Invalid Biller Pay TV  provider [Error code 691]";
                break;
               
                case 692:
                resp = "Invalid Biller Category [Error code 692]";
                break;
                case 693:
                resp = "Invalid Biller Airtime Network [Error code 693]";
                break;
                case 694:
                resp = "Auto reversal successful [Error code 694]";
                break;
                 case 695:
                resp = "Auto reversal failed, kindly contact administrator or raise a dispute [Error code 695]";
                break;
                case 696:
                resp = "your OTP has expired [Error code 696]";
                break;
                case 697:
                resp = "Invalid Transaction Type [Error code 697]";
                break;
                case 698:
                resp = "Your Mobile Profile already exists, Log in to your profile [Error code 698]";
                break;
                case 699:
                resp = "INVALID_IMEI [Error code 699]";
                break;
                case 700:
                resp = "You have entered an Invalid Transaction PIN, kindly check and try again [Error code 700]";
                break;
                case 701:
                resp = "You have entered an Invalid Account Number, kindly check and try again.[Error code 701 tag name srcAccount]";
                break;
                case 702:
                resp = "Sorry, you cannot log in at this time. kindly try again later. [Error code 702]";
                break;
                case 703:
                resp = "Sorry, we have encountered an error, kindly contact the administrator [Error code 703]";
                break;
                case 704:
                resp = "Sorry, a database error has occurred kindly contact the administrator.[Error code 704]";
                break;
                case 705:
                resp = "INVALID_SESSION_ID, kindly check and try again [Error code 705]";
                break;
                case 706:
                resp = "The Cheque is not valid for this account, kindly check and try again.[Error code 706]";
                break;
                case 707:
                resp = "You have entered an Invalid Amount, kindly check and try again [Error code 707]";
                break;
                case 708:
                resp = "You have entered an Invalid Narration, kindly check and try again.[Error code 708]";
                break;
                case 709:
                resp = "You have entered an Invalid Beneficiary Bank, kindly contact the Beneficiary Bank and try again later [Error code 709 tag name destinationInstitutionCode, expected length is 6 digits]";
                break;
                case 710:
                resp = "You have entered an Invalid Transaction Pin, kindly check and try again.[Error code 710]";
                break;
                case 711:
                resp = "Sorry, your token has expired, kindly contact admin.[Error code 711]";
                break;
                /*
                case 711:
                resp = "Sorry, you cannot log in at this time. Please try again later.[Error code 711]";
                break;
                */
                case 712:
                resp = "Sorry, decryption error, kindly contact admin [Error code 712]";
                break;
                case 713:
                resp = "Sorry, you cannot log in at this time. Please try again later [Error code 713]";
                break;
                case 714:
                resp = "Sorry, you cannot log in at this time. Please check your internet connection and try again later.[Error code 714]";
                break;
                case 715:
                resp = "Duplicate transaction detected, kindly  confirm transaction status from the beneficiary before you try again.[Error code 715]";
                break;
                case 716:
                resp = "Sorry, you have entered an invalid requestId. Please correct and try again. [Error code 716]";
                break;
                case 717:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 717]";
                break;
                case 718:
                resp = "You have entered an Invalid Beneficiary Account number, please check and try again. [Error code 718]";
                break;
                case 719:
                resp = "You have entered an Invalid First name, please check and try again. [Error code 719]";
                break;
                case 720:
                resp = "You have entered an Invalid Last name, please check and try again. [Error code 720]";
                break;
                case 721:
                resp = "You have entered an Invalid email address, please check and try again. [Error code 721 tag name emailAddress]";
                break;
                case INVALID_MSISDN:
                resp = "Sorry, you have provided an invalid mobile no .[Error code 722 json tag name mobileNo]";
                break;
                case 723:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 723]";
                break;
                case 724:
                resp = "Your User Profile already exist. Log in to your profile. [Error code 724]";
                break;
                case 725:
                resp = "The confirm pin you enter does not match, please check and try again. [Error code 725]";
                break;
                case 726:
                resp = "You have entered an Invalid transaction pin, please check and try again.[Error code 726]";
                break;
                case 727:
                resp = "Transaction on this account is not allowed at this time, please try again later. [Error code 727]";
                break;
                case 728:
                resp = "You have entered an Invalid username, please check and try again. [Error code 728]";
                break;
                case 729:
                resp = "You have entered an Invalid login detail, please enter the correct login details [Error code 729]";
                break;
                
                case 850: // mapped to auth service resp
                resp = "You have entered an Invalid login detail, please enter the correct login details.[Error code 850]";
                break;
                case 730:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 730]";
                break;
                case 731:
                resp = "The Mobile number does not match the registered number on the account. Please contact the bank for the registered phone number and try again. [Error code 731]";
                break;
                case 732:
                resp = "There is no account attached to this number. Please contact the Bank and try again later [Error code 732]";
                break;
                case IO_EXCEPTION:
                resp = "IO_EXCEPTION, Please try again later. [Error code 733]";
                break;
                case 734:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 734]";
                break;
                case 735:
                resp = "Invalid Sender Kyc. [Error code 735]";
                break;
                case 736:
                resp = "Invalid beneficiary KYC.[Error code 736]";
                break;
                case 737:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 737]";
                break;
                case 738:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later [Error code 738]";
                break;
                case 739:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later.[Error code 739]";
                break;
                case 740:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 740]";
                break;
                case 741:
                resp = "Sorry, you cannot log in at this time. Please try again later. [Error code 741]";
                break;
                
                case 742:
                resp = "Sorry, you cannot log in at this time. Please try again later.[Error code 742]";
                break;
                
                case 743:
                resp = "The verify pin you enter does not match, please check and try again [Error code 743]";
                break;
                
                case 744:
                resp = "The pin you enter is too easy to guess, please use more difficult pin to guess [Error code 744]";
                break;
                
                case 745:
                resp = "Transaction on this account is not allowed at this time, please try again later. [Error code 745]";
                break;
                case 746:
                resp = "Transaction on this account is not allowed at this time, please contact the Bank [Error code 746]";
                break;
                case 747:
                resp = "Daily transaction limit has been exceeded on this account; transaction is not allowed at this time. [Error code 747]";
                break;
                case 748:
                resp = "Sorry, you cannot because you have an invalid user JWT [Error code 748] ";
                break;
                case 749:
                resp = "The confirm password you enter does not match, please check and try again [Error code 749 tag name verifyPassword]";
                break;
               case 750:
                resp = "The new password you entered does not match the password provided, please check and try again [Error code 750]";
                break;
               case 751:
                resp = "You have entered an Invalid Password, please check and try again [Error code 751]";
                break;
                case 752:
                resp = "The password you entered is too easy to guess, please use more difficult password to guess. [Error code 752]";
                break;
                case 753:
                resp = "You have entered an Invalid New Password, please check and try again. [Error code 753]";
                break;
                case 754:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 754]";
                break;
               case 755:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 755]";
                break;
                case 756:
                resp = "You have entered an Invalid Beneficiary Bank, please check and try again. [Error code 756]";
                break;
                case 757:
                resp = "You have entered an Invalid Beneficiary Account Number, please check and try again [Error code 757]";
                break;
                case 758:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later .[Error code 758]";
                break;
                case 759:
                resp = "You have entered an Invalid Beneficiary, please check and try again [Error code 759]";
                break;
                case 760:
                resp = "Sorry, there is no record for the requestId shared is not found on the systems. Please check and try again later. [Error code 760]";
                break;
                case 761:
                resp = "Multiple failed logins detected; your account has been locked. Please contact the Bank. [Error code 761]";
                break;
                
                case INVALID_CONFIRM_PASSWORD:
                resp = "Sorry your confirm password value does not match given password [Error code 854]";
                break;
                case 859:
                resp = "Sorry you do not have enough funds for this transaction, please fund your account and try again [Error code 762]";
                break;
                case 860:
                resp = "This transaction requires an OTP verification, kindly verify the OTP sent to you,  to complete";
                break;
                case 861:
                resp = "Invalid Device Finger Print [Error code 861]";
                break;
                case 862:
                resp = " Device Swap detected, please verify OTP to continue [Error code 862]";
                break;
                
                case 863:
                resp = "Sorry you selected an invalid start date [Error code 863]";
                break;
                case 864:
                resp = "Sorry you do selected an invalid end date [Error code 864]";
                break;
                case 865:
                resp = "Sorry your start data must be a date before your end date [Error code 865]";
                break;
                case 866:
                resp = "Sorry you have entered an invalid page Id or page size [Error code 866]";
                break;
                case 867:
                resp = "Sorry you have selected an invalid entity [Error code 867]";
                break;
                case 868:
                resp = "Sorry you have entered an invalid description [Error code 868]";
                break;
              
                /*
                    public static final int INVALID_START_DATE = 863;
    public static final int INVALID_END_DATE = 864;
    public static final int DATE_DISPARITY = 865;
    public static final int INVALID_PAGE_ID_OR_FETCH_SIZE = 866;
                */
                case 901:
                resp = "Sorry, you cannot log in at this time. Please try again later [Error code 901]";
                break;
                default:
                 resp= "Sorry, you cannot log in at this time. Please try again later. [Error code "+code+" ]";
                break;
            
            }
          }
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        
        
    return resp==null?"NA":resp.replaceAll("_", " ").toLowerCase();
    }
    
    public static String doErrorDesc(int code) {
        log.info(" code : "+code);
        String resp = "";
        try 
        {
          
             switch(code)
             {
                case 200:
                resp = "Operation successfull";
                break;
                case 201:
                resp = "Your Mobile Profile has been successfully created. Login to Change your transaction pin";
                break;
                case 202:
                resp = "input successfully accepted";
                break;
                case BAD_REQUEST:
                resp = "BAD_REQUEST  [Error code 400]";
                break;
                case 401:
                resp = "Unauthorized Request Ensure your request has the required attributes and you are authorized to use this service  [Error code 401]";
                break;
               
                case EXPIRED_SUBSCRIPTION:
                resp = "EXPIRED_SUBSCRIPTION  [Error code 607]";
                break;
                case UHURU_SUBCRIPTION_ERROR:
                resp = "UHURU_SUBCRIPTION_ERROR  [Error code 608]";
                break;
                 case INVALID_BILLER_ITEM:
                resp = "INVALID_BILLER_ITEM  [Error code 609]";
                break;
                case INACTIVE_SUBSCRIPTION:
                resp = "INACTIVE_SUBSCRIPTION kindy resubscribe and try again[Error code 610]";
                break;
                case WHEEL_CONFIG_ERROR:
                resp = "WHEEL_CONFIG_ERROR [Error code 611]";
                break;
                case SPIN_ENTRY_ERROR:
                resp = "SPIN_ENTRY_ERROR [Error code 612]";
                break;
                 case MAX_ALLOWED_PLAY_COUNT_EXCEEDED:
                resp = "MAX_ALLOWED_PLAY_COUNT_EXCEEDED [Error code 613]";
                break;
                case UN_SUBCRIPTION_ERROR:
                resp = "UN_SUBCRIPTION_ERROR [Error code 614]";
                break;
                case INVALID_PRODUCT_ID:
                resp = "INVALID_PRODUCT_ID [Error code 615]";
                break;
                case INVALID_SENDER_ID:
                resp = "INVALID_SENDER_ID [Error code 616]";
                break;
                case INVALID_MESSAGE:
                resp = "INVALID_MESSAGE [Error code 617]";
                break;
                case INVALID_SERVICEID:
                resp = "INVALID_SERVICEID [Error code 618]";
                break;
                case SUBCRIPTION_ERROR:
                resp = "SUBCRIPTION_ERROR [Error code 619]";
                break;
                case INVALID_PAGE_ID:
                resp = "INVALID_PAGE_ID [Error code 620]";
                break;
                case INVALID_PAGE_NAME:
                resp = "INVALID_PAGE_NAME [Error code 621]";
                break;
                case INVALID_WIN_TYPE:
                resp = "INVALID_WIN_TYPE [Error code 622]";
                break;
                case INVALID_WHEEL_LABEL:
                resp = "INVALID_WHEEL_LABEL [Error code 623]";
                break;
                case NOT_SUBSCRIBED:
                resp = "NOT_SUBSCRIBED [Error code 624]";
                break;
                case ALREADY_SUBSCRIBED:
                resp = "ALREADY_SUBSCRIBED [Error code 625]";
                break;
                case INVALID_SPIN_VALUE:
                resp = "INVALID_SPIN_VALUE [Error code 626]";
                break;
                case INVALID_AUTO_OPTION:
                resp = "INVALID_AUTO_OPTION [Error code 627]";
                break;
                case INVALID_OF_DAYS:
                resp = "INVALID_OF_DAYS [Error code 628]";
                break;
                case INVALID_PLAN:
                resp = "INVALID_PLAN [Error code 629]";
                break;
                case NO_ANSWER_SETUP_FOR_QUESTION:
                resp = "NO_ANSWER_SETUP_FOR_QUESTION [Error code 630]";
                break;
                case QUESTION_ALREADY_ANSWERED:
                resp = "QUESTION_ALREADY_ANSWERED [Error code 631]";
                break;
                case MAX_NUMBER_OF_ANSWER_OPTIONS_FOR_QUESTION:
                resp = "MAX_NUMBER_OF_ANSWER_OPTIONS_FOR_QUESTION [Error code 632]";
                break;
                case NOT_YOUR_QUESTION:
                resp = "NOT_YOUR_QUESTION [Error code 633]";
                break;
                case INVALID_ANSWER_TAG:
                resp = "INVALID_ANSWER_TAG [Error code 634]";
                break;
                case QUESTION_CAN_HAVE_ONLY_ONE_ANSWER:
                resp = "QUESTION_CAN_HAVE_ONLY_ONE_ANSWER [Error code 635]";
                break;
                case INVALID_ANSWER:
                resp = "INVALID_ANSWER [Error code 636]";
                break;
                 case INVALID_ANSWER_INDICATOR:
                resp = "INVALID_ANSWER_INDICATOR [Error code 637]";
                break;
                case INVALID_ANSWER_SELECTED:
                resp = "INVALID_ANSWER_SELECTED tag name optionsTag [Error code 638]";
                break;
                 case INVALID_HOW_TO_PLAY_STEP_LABEL:
                resp = "INVALID_HOW_TO_PLAY_STEP_LABEL [Error code 639]";
                break;
                case INVALID_HOW_TO_PLAY_STEP_MSG:
                resp = "INVALID_HOW_TO_PLAY_STEP_MSG [Error code 640]";
                break;
                case INVALID_PLAY_STEP:
                resp = "INVALID_PLAY_STEP [Error code 641]";
                break;
                case INVALID_PLAY_STEP_LABEL:
                resp = "INVALID_PLAY_STEP_LABEL [Error code 642]";
                break;
                case INVALID_HEADER_INFO:
                resp = "INVALID_HEADER_INFO [Error code 643]";
                break;
                case INVALID_HEADER_INFO_DATA:
                resp = "INVALID_HEADER_INFO_DATA [Error code 644]";
                break;
                case INVALID_CTA_ONE_LABEL:
                resp = "INVALID_STATUS [Error code 645]";
                break;
                case INVALID_CTA_TWO_LABEL:
                resp = "INVALID_CTA_TWO_LABEL [Error code 646]";
                break;
                case INVALID_PARAM_VALUE:
                resp = "INVALID_PARAM_VALUE [Error code 647]";
                break;
                case INVALID_PARAM_NAME:
                resp = "INVALID_PARAM_NAME [Error code 648]";
                break;
               
                  case INVALID_CLIENT:
                resp = "INVALID_CLIENT [Error code 649]";
                break;
               
                case MAX_NUMBER_OF_API_CLIENTS_EXCEEDED:
                resp = "MAX_NUMBER_OF_API_CLIENTS_EXCEEDED [Error code 650]";
                break;
               
                case INVALID_CUSTOMER_CODE:
                resp = "INVALID_CUSTOMER_CODE [Error code 651]";
                break;
                
                case INVALID_TOKEN_AGE:
                resp = "INVALID_TOKEN_AGE [Error code 652]";
                break;
                
                case INVALID_STATUS:
                resp = "INVALID_STATUS [Error code 653]";
                break;
                case INVALID_CLIENT_NAME:
                resp = "INVALID_CLIENT_NAME [Error code 654]";
                break;
                case INVALID_COUNTRY:
                resp = "INVALID_COUNTRY [Error code 655]";
                break;
                 case INVALID_STATE:
                resp = "INVALID_STATE [Error code 656]";
                break;
                
                case INVALID_GENDER:
                resp = "INVALID_GENDER [Error code 657]";
                break;
                case INVALID_PAYMENT_BENEFICIARY:
                resp = "INVALID_PAYMENT_BENEFICIARY [Error code 658]";
                break;
                case INVALID_IP_ADDRESS:
                resp = "INVALID_IP_ADDRESS [Error code 659]";
                break;
                case OBJECT_CONFLICT:
                resp = "OBJECT_CONFLICT [Error code 660]";
                break;
                case INVALID_OPERATOR:
                resp = "INVALID_OPERATOR [Error code 661]";
                break;
                case PENDING_AUTHORIZATION:
                resp = "PENDING_AUTHORIZATION [Error code 662]";
                break;
                case INVALID_ORIGINATOR_NARRATION:
                resp = "INVALID_ORIGINATOR_NARRATION [Error code 663]";
                break;
                case INVALID_EASYPAY_TOKEN:
                resp = "INVALID_EASYPAY_TOKEN [Error code 664]";
                break;   
                case INVALID_SRC_ACCOUNT: //666
                resp = "INVALID_SRC_ACCOUNT [Error code 665]";
                break;
                case INVALID_CHANNEL_CODE: //666
                resp = "Invalid Channel code [Error code 666]";
                break;
                //case 667:
               // resp = "Auto Reversal Failure [Error code 667]";
                //break;
                
                
                case INVALID_EASYPAY_TRANID:
                resp = "Invalid easy pay tranid should be 30 characters fixed length with the clienr code of 6 digits the 12 digit date componen in YYMMDDHHMMSS + random 12 digits [Error code 666]";
                break;
                case 668:
                resp = "Successfully Reversed [Error code 668]";
                break;
                case 669:
                resp = "Invalid role name [Error code 669]";
                break;
                
                case 670:
                resp = "Invalid role description [Error code 670]";
                break;
                case 671:
                resp = "Invalid upper limit [Error code 671]";
                break;
                
                case 672:
                resp = "Invalid limit lower cannot be more that upper and they cannot be equal [Error code 672]";
                break;
                
                case 673:
                resp = "Invalid Description [Error code 673]";
                break;
                case 674:
                resp = "Bill Payment Error  [Error code 674]";
                break;
                case 675:
                resp = "Invalid payment type  [Error code 675]";
                break;
                case 676:
                resp = "Invalid search key or value  [Error code 676]";
                break;
                case 677:
                resp = "Invalid profile image  [Error code 677]";
                break;
                case 678:
                resp = "Invalid dispute action  [Error code 678]";
                break;
                case 679:
                resp = "Invalid dispute action data [Error code 679]";
                break;
                case INVALID_IMAGE_FORMAT:
                resp = "INVALID_IMAGE_FORMAT [Error code 680]";
                
                break;
                case 681:
                resp = "INVALID_CATEGORY [Error code 681]";
                break;
                case 682:
                resp = "Invalid dispute subject [Error code 682]";
                break;
                case 683:
                resp = "Invalid dispute subject [Error code 683]";
                break;
                case 684:
                resp = "Dispute action ongoing [Error code 684]";
                break;
                case 685:
                resp = "Invalid user [Error code 685]";
                break;
                case 686:
                resp = "Invalid role [Error code 686]";
                break;
                case 687:
                resp = "Invalid Biller Name [Error code 687]";
                break;
                case 688:
                resp = "INVALID_QUESTION [Error code 688]";
                break;
                case 689:
                resp = "Invalid customer Id [Error code 689 tag name customerId]";
                break;
                case 690:
                resp = "Invalid Biller Category Name [Error code 690]";
                break;
                
                case 691:
                resp = "Invalid Biller Pay TV  provider [Error code 691]";
                break;
               
                case 692:
                resp = "Invalid Biller Category [Error code 692]";
                break;
                case 693:
                resp = "Invalid Biller Airtime Network [Error code 693]";
                break;
                case 694:
                resp = "Auto reversal successful [Error code 694]";
                break;
                 case 695:
                resp = "Auto reversal failed, kindly contact administrator or raise a dispute [Error code 695]";
                break;
                case 696:
                resp = "your OTP has expired [Error code 696]";
                break;
                case 697:
                resp = "Invalid Transaction Type [Error code 697]";
                break;
                case 698:
                resp = "Your Mobile Profile already exists, Log in to your profile [Error code 698]";
                break;
                case 699:
                resp = "INVALID_IMEI [Error code 699]";
                break;
                case 700:
                resp = "You have entered an Invalid Transaction PIN, kindly check and try again [Error code 700]";
                break;
                case 701:
                resp = "You have entered an Invalid Account Number, kindly check and try again.[Error code 701 tag name srcAccount]";
                break;
                case 702:
                resp = "Sorry, you cannot log in at this time. kindly try again later. [Error code 702]";
                break;
                case 703:
                resp = "Sorry, we have encountered an error, kindly contact the administrator [Error code 703]";
                break;
                case 704:
                resp = "Sorry, a database error has occurred kindly contact the administrator.[Error code 704]";
                break;
                case 705:
                resp = "INVALID_SESSION_ID, kindly check and try again [Error code 705]";
                break;
                case 706:
                resp = "The Cheque is not valid for this account, kindly check and try again.[Error code 706]";
                break;
                case 707:
                resp = "You have entered an Invalid Amount, kindly check and try again [Error code 707]";
                break;
                case 708:
                resp = "You have entered an Invalid Narration, kindly check and try again.[Error code 708]";
                break;
                case 709:
                resp = "You have entered an Invalid Beneficiary Bank, kindly contact the Beneficiary Bank and try again later [Error code 709 tag name destinationInstitutionCode, expected length is 6 digits]";
                break;
                case 710:
                resp = "You have entered an Invalid Transaction Pin, kindly check and try again.[Error code 710]";
                break;
                case 711:
                resp = "Sorry, your token has expired, kindly contact admin.[Error code 711]";
                break;
                /*
                case 711:
                resp = "Sorry, you cannot log in at this time. Please try again later.[Error code 711]";
                break;
                */
                case 712:
                resp = "Sorry, decryption error, kindly contact admin [Error code 712]";
                break;
                case 713:
                resp = "Sorry, you cannot log in at this time. Please try again later [Error code 713]";
                break;
                case 714:
                resp = "Sorry, you cannot log in at this time. Please check your internet connection and try again later.[Error code 714]";
                break;
                case 715:
                resp = "Duplicate transaction detected, kindly  confirm transaction status from the beneficiary before you try again.[Error code 715]";
                break;
                case 716:
                resp = "Sorry, you have entered an invalid requestId. Please correct and try again. [Error code 716]";
                break;
                case 717:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 717]";
                break;
                case 718:
                resp = "You have entered an Invalid Beneficiary Account number, please check and try again. [Error code 718]";
                break;
                case 719:
                resp = "You have entered an Invalid First name, please check and try again. [Error code 719]";
                break;
                case 720:
                resp = "You have entered an Invalid Last name, please check and try again. [Error code 720]";
                break;
                case 721:
                resp = "You have entered an Invalid email address, please check and try again. [Error code 721 tag name emailAddress]";
                break;
                case INVALID_MSISDN:
                resp = "Sorry, you have provided an invalid mobile no .[Error code 722 json tag name mobileNo]";
                break;
                case 723:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 723]";
                break;
                case 724:
                resp = "Your User Profile already exist. Log in to your profile. [Error code 724]";
                break;
                case 725:
                resp = "The confirm pin you enter does not match, please check and try again. [Error code 725]";
                break;
                case 726:
                resp = "You have entered an Invalid transaction pin, please check and try again.[Error code 726]";
                break;
                case 727:
                resp = "Transaction on this account is not allowed at this time, please try again later. [Error code 727]";
                break;
                case 728:
                resp = "You have entered an Invalid username, please check and try again. [Error code 728]";
                break;
                case 729:
                resp = "You have entered an Invalid login detail, please enter the correct login details [Error code 729]";
                break;
                
                case 850: // mapped to auth service resp
                resp = "You have entered an Invalid login detail, please enter the correct login details.[Error code 850]";
                break;
                case 730:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 730]";
                break;
                case 731:
                resp = "The Mobile number does not match the registered number on the account. Please contact the bank for the registered phone number and try again. [Error code 731]";
                break;
                case 732:
                resp = "There is no account attached to this number. Please contact the Bank and try again later [Error code 732]";
                break;
                case IO_EXCEPTION:
                resp = "IO_EXCEPTION, Please try again later. [Error code 733]";
                break;
                case 734:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 734]";
                break;
                case 735:
                resp = "Invalid Sender Kyc. [Error code 735]";
                break;
                case 736:
                resp = "Invalid beneficiary KYC.[Error code 736]";
                break;
                case 737:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 737]";
                break;
                case 738:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later [Error code 738]";
                break;
                case 739:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later.[Error code 739]";
                break;
                case 740:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 740]";
                break;
                case 741:
                resp = "Sorry, you cannot log in at this time. Please try again later. [Error code 741]";
                break;
                
                case 742:
                resp = "Sorry, you cannot log in at this time. Please try again later.[Error code 742]";
                break;
                
                case 743:
                resp = "The verify pin you enter does not match, please check and try again [Error code 743]";
                break;
                
                case 744:
                resp = "The pin you enter is too easy to guess, please use more difficult pin to guess [Error code 744]";
                break;
                
                case 745:
                resp = "Transaction on this account is not allowed at this time, please try again later. [Error code 745]";
                break;
                case 746:
                resp = "Transaction on this account is not allowed at this time, please contact the Bank [Error code 746]";
                break;
                case 747:
                resp = "Daily transaction limit has been exceeded on this account; transaction is not allowed at this time. [Error code 747]";
                break;
                case 748:
                resp = "Sorry, you cannot because you have an invalid user JWT [Error code 748] ";
                break;
                case 749:
                resp = "The confirm password you enter does not match, please check and try again [Error code 749 tag name verifyPassword]";
                break;
               case 750:
                resp = "The new password you entered does not match the password provided, please check and try again [Error code 750]";
                break;
               case 751:
                resp = "You have entered an Invalid Password, please check and try again [Error code 751]";
                break;
                case 752:
                resp = "The password you entered is too easy to guess, please use more difficult password to guess. [Error code 752]";
                break;
                case 753:
                resp = "You have entered an Invalid New Password, please check and try again. [Error code 753]";
                break;
                case 754:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 754]";
                break;
               case 755:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later. [Error code 755]";
                break;
                case 756:
                resp = "You have entered an Invalid Beneficiary Bank, please check and try again. [Error code 756]";
                break;
                case 757:
                resp = "You have entered an Invalid Beneficiary Account Number, please check and try again [Error code 757]";
                break;
                case 758:
                resp = "Sorry, you cannot perform transaction at this time. Please try again later .[Error code 758]";
                break;
                case 759:
                resp = "You have entered an Invalid Beneficiary, please check and try again [Error code 759]";
                break;
                case 760:
                resp = "Sorry, there is no record for the requestId shared is not found on the systems. Please check and try again later. [Error code 760]";
                break;
                case 761:
                resp = "Multiple failed logins detected; your account has been locked. Please contact the Bank. [Error code 761]";
                break;
                case 859:
                resp = "Sorry you do not have enough funds for this transaction, please fund your account and try again [Error code 762]";
                break;
                case 860:
                resp = "This transaction requires an OTP verification, kindly verify the OTP sent to you,  to complete";
                break;
                case 861:
                resp = "Invalid Device Finger Print [Error code 861]";
                break;
                case 862:
                resp = " Device Swap detected, please verify OTP to continue [Error code 862]";
                break;
                
                case 863:
                resp = "Sorry you selected an invalid start date [Error code 863]";
                break;
                case 864:
                resp = "Sorry you do selected an invalid end date [Error code 864]";
                break;
                case 865:
                resp = "Sorry your start data must be a date before your end date [Error code 865]";
                break;
                case 866:
                resp = "Sorry you have entered an invalid page Id or page size [Error code 866]";
                break;
                case 867:
                resp = "Sorry you have selected an invalid entity [Error code 867]";
                break;
                case 868:
                resp = "Sorry you have entered an invalid description [Error code 868]";
                break;
              
                case 901:
                resp = "Sorry, you cannot log in at this time. Please try again later [Error code 901]";
                break;
                default:
                 resp= "Sorry, you cannot log in at this time. Please try again later. [Error code "+code+" ]";
                break;
            
            }
          
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        
        
    return resp==null?"NA":resp.replaceAll("_", " ").toLowerCase();
    }
    
   
  public static String  getErrorNarration(String err)
  {
      System.out.println(" getErrorNarration ### err = " +  err);
      String resp = "";
      if(err == null || err.trim().equals("")) err = "06";
      HashMap<String, String>  errorMap = new  HashMap<>();
                errorMap.put("00", "Approved or completed successfully");
                errorMap.put("01", "Status unknown, please wait for settlement report");
                errorMap.put("03", "Invalid Sender");
                errorMap.put("05", "Do not honor");
                errorMap.put("06", "Error");
                errorMap.put("07", "Invalid Account");
                errorMap.put("08", "Account Name Mismatch");
                errorMap.put("09", "Request processing in progress");
                errorMap.put("12", "Invalid transaction");
                errorMap.put("13", "Invalid Amount");
                errorMap.put("14", "Invalid Batch Number");
                errorMap.put("15", "Invalid Session or Record ID");
                errorMap.put("16", "Unknown Bank Code");
                errorMap.put("17", "Invalid Channel");
                errorMap.put("18", "Wrong Method Call");
                errorMap.put("21", "No action taken");
                errorMap.put("25", "Unable to locate record");
                errorMap.put("26", "Duplicate record");
                errorMap.put("30", "Format error");
                errorMap.put("34", "Suspected fraud");
                errorMap.put("35", "Contact sending bank");
                errorMap.put("51", "No sufficient funds");
                errorMap.put("57", "Transaction not permitted to sender");
                errorMap.put("58", "Transaction not permitted on channel");
                errorMap.put("61", "Transfer limit Exceeded");
                errorMap.put("63", "Security violation");
                errorMap.put("65", "Exceeds withdrawal frequency");
                errorMap.put("68", "Response received too late");
                errorMap.put("69", "Unsuccessful Account/Amount block");
                errorMap.put("70", "Unsuccessful Account/Amount unblock");
                errorMap.put("71", "Empty Mandate Reference Number");
                errorMap.put("88", "SOAP message could not be sent"); 
                errorMap.put("91", "Bank not available");
                errorMap.put("92", "Routing error");
                errorMap.put("93", "INVALID MERCHANT ID PASSED");
                errorMap.put("94", "Duplicate transaction");
                errorMap.put("95", "Memcol Service Unavailable");
                errorMap.put("96", "System malfunction");
                errorMap.put("97", "Timeout waiting for response from destination");
                
   
    if(err != null && errorMap != null)
    {
        try
        {
            resp = errorMap.get(err.trim());
        }
        catch(Exception ex)
        {
             resp =  "06";
        }
    }
    else
    {
       resp =  "06";
    }
      
      
      return (resp==null || resp.trim().equals(""))?"NA":resp.replaceAll("_", " ").toLowerCase();
  }
    
    
    
}
