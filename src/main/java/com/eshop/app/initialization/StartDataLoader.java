package com.eshop.app.initialization;

import com.eshop.app.model.bascet.Basket;
import com.eshop.app.model.bascet.BasketKey;
import com.eshop.app.model.bascet.BasketRepository;
import com.eshop.app.model.category.Category;
import com.eshop.app.model.category.CategoryRepository;
import com.eshop.app.model.characteristic.Characteristic;
import com.eshop.app.model.characteristic.CharacteristicRepository;
import com.eshop.app.model.comment.Comment;
import com.eshop.app.model.comment.CommentRepository;
import com.eshop.app.model.image.Image;
import com.eshop.app.model.image.ImageRepository;
import com.eshop.app.model.image.ImageService;
import com.eshop.app.model.product.Product;
import com.eshop.app.model.product.ProductRepository;
import com.eshop.app.model.report.Report;
import com.eshop.app.model.report.ReportRepository;
import com.eshop.app.model.report.ReportType;
import com.eshop.app.model.user.User;
import com.eshop.app.model.user.UserRepository;
import com.eshop.app.model.user.UserRole;
import java.io.File;
import java.nio.file.Files;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartDataLoader implements ApplicationRunner {

  private final UserRepository userRepository;
  private final ImageService imageService;
  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;
  private final CharacteristicRepository characteristicRepository;
  private final ReportRepository reportRepository;
  private final CommentRepository commentRepository;
  private final BasketRepository basketRepository;

  @Value("${first-admin-email}")
  private String email;

  @Autowired
  public StartDataLoader(UserRepository userRepository, ImageService imageService,
      CategoryRepository categoryRepository, ProductRepository productRepository,
      CharacteristicRepository characteristicRepository, ReportRepository reportRepository,
      CommentRepository commentRepository, BasketRepository basketRepository) {
    this.userRepository = userRepository;
    this.imageService = imageService;
    this.categoryRepository = categoryRepository;
    this.productRepository = productRepository;
    this.characteristicRepository = characteristicRepository;
    this.reportRepository = reportRepository;
    this.commentRepository = commentRepository;
    this.basketRepository = basketRepository;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    Image defaultProfileImage = imageService.getDefaultProfilePicture();
    User firstAdmin = new User(null, email, email, UserRole.ADMIN, null, defaultProfileImage);
    firstAdmin = userRepository.save(firstAdmin);

    Category c1 = new Category(null, "Pet food", new ArrayList<>(List.of("For", "Type", "Weight", "From age")));
    c1 = categoryRepository.save(c1);

    Category c2 = new Category(null, "Toys", new ArrayList<>(List.of("Type")));
    c2 = categoryRepository.save(c2);

    Category c3 = new Category(null, "Care", new ArrayList<>(List.of("For", "From age")));
    c3 = categoryRepository.save(c3);

    Category c4 = new Category(null, "Limiters", new ArrayList<>(List.of("Type")));
    c4 = categoryRepository.save(c4);

    Category c5 = new Category(null, "Fillers", new ArrayList<>(List.of("Material", "Weight")));
    c5 = categoryRepository.save(c5);

    User firstManager = new User(null, "Katerina", "zhurzhakaterina@gmail.com", UserRole.MANAGER, c1, defaultProfileImage);
    firstManager = userRepository.save(firstManager);

    User firstCustomer = new User(null, "Arthur", "bielobrov.8864899@stud.op.edu.ua", UserRole.CUSTOMER, null, defaultProfileImage);
    firstCustomer = userRepository.save(firstCustomer);

    Product p11 = new Product(null, "Purina Gourmet Gold",
        "Pack of wet cat food Purina Gourmet Gold Tender Chicken and Carrot Chops 12 pcs 85 g ",
        209D, c1, imageService.getImageByPath("292793371.jpg"), new ArrayList<>());
    p11 = productRepository.save(p11);

    Characteristic c111 = new Characteristic(null, "For", "Cats", p11);
    c111 = characteristicRepository.save(c111);
    Characteristic c112 = new Characteristic(null, "Type", "Wet", p11);
    c112 = characteristicRepository.save(c112);
    Characteristic c113 = new Characteristic(null, "Weight", "85g", p11);
    c113 = characteristicRepository.save(c113);
    Characteristic c114 = new Characteristic(null, "From age", "12 month", p11);
    c114 = characteristicRepository.save(c114);

    Report r111 = new Report(null, firstCustomer, new Date(System.currentTimeMillis()), "Have bought 2 weeks ago, the cat was so happy.",
        5, ReportType.REVIEW, new ArrayList<>(), p11);
    r111 = reportRepository.save(r111);

    Comment co111 = new Comment(null, firstCustomer, new Date(System.currentTimeMillis() + 60 * 60 * 24), "My cat have just got poisoned!!!!! If I can, I'd change my grade to 0!!!", r111);
    co111 = commentRepository.save(co111);

    Report r112 = new Report(null, firstCustomer, new Date(System.currentTimeMillis() + 60 * 80 * 24 ), null,
        1, ReportType.REVIEW, new ArrayList<>(), p11);
    r112 = reportRepository.save(r112);

    Report q111 = new Report(null, firstCustomer, new Date(System.currentTimeMillis() + 60 * 60 * 24 * 2), "How can I get my moneys back??",
        null, ReportType.QUESTION, new ArrayList<>(), p11);
    q111 = reportRepository.save(q111);

    Comment co112 = new Comment(null, firstManager, new Date(System.currentTimeMillis() + 60 * 80 * 24 * 2), "Contact us to support.eshop@gmail.com to get information about money policy.", q111);
    co112 = commentRepository.save(co112);

    Report q112 = new Report(null, firstCustomer, new Date(System.currentTimeMillis() + 60 * 60 * 24 * 7), "Seems like my cat have poisoned due-to different thing... Can I buy more of this product?",
        null, ReportType.QUESTION, new ArrayList<>(), p11);
    q112 = reportRepository.save(q112);

    Product p12 = new Product(null, "Whiskas chicken",
        "Whiskas dry food for sterilized cats with chicken 14 kg",
        1713D, c1, imageService.getImageByPath("10778920.png"), new ArrayList<>());
    p12 = productRepository.save(p12);

    Characteristic c121 = new Characteristic(null, "For", "Cats", p12);
    c121 = characteristicRepository.save(c121);
    Characteristic c122 = new Characteristic(null, "Type", "Dry", p12);
    c122 = characteristicRepository.save(c122);
    Characteristic c123 = new Characteristic(null, "Weight", "14kg", p12);
    c123 = characteristicRepository.save(c123);
    Characteristic c124 = new Characteristic(null, "From age", "12 month", p12);
    c124 = characteristicRepository.save(c124);
    Characteristic c125 = new Characteristic(null, "Cat type", "Neutered", p12);
    c125 = characteristicRepository.save(c125);

    Product p13 = new Product(null, "Purina Pro Plan FortiFlora",
        "Complementary food for adult dogs and puppies Purina Pro Plan Canine Probiotic FortiFlora 30 g",
        410D, c1, imageService.getImageByPath("303419125.jpg"), new ArrayList<>());
    p13 = productRepository.save(p13);

    Characteristic c131 = new Characteristic(null, "For", "Dogs", p13);
    c131 = characteristicRepository.save(c131);
    Characteristic c132 = new Characteristic(null, "Type", "Natural", p13);
    c132 = characteristicRepository.save(c132);
    Characteristic c133 = new Characteristic(null, "Weight", "30g", p13);
    c133 = characteristicRepository.save(c133);
    Characteristic c134 = new Characteristic(null, "From age", "Any", p13);
    c134 = characteristicRepository.save(c134);
    Characteristic c135 = new Characteristic(null, "Count per package", "30", p13);
    c135 = characteristicRepository.save(c135);

    Product p14 = new Product(null, "Vitapol 800 g",
        "Hay for rodents Vitapol 800 g",
        610D, c1, imageService.getImageByPath("48692007.jpg"), new ArrayList<>());
    p14 = productRepository.save(p14);

    Characteristic c141 = new Characteristic(null, "For", "Rodents", p14);
    c141 = characteristicRepository.save(c141);
    Characteristic c142 = new Characteristic(null, "Type", "Hay", p14);
    c142 = characteristicRepository.save(c142);
    Characteristic c143 = new Characteristic(null, "Weight", "800g", p14);
    c143 = characteristicRepository.save(c143);
    Characteristic c144 = new Characteristic(null, "From age", "Any", p14);
    c144 = characteristicRepository.save(c144);

    Product p21 = new Product(null, "Plush beaver",
        "Plush beaver with imitation of sound Trixie 35910 27 cm",
        467D, c2, imageService.getImageByPath("10781944.jpg"), new ArrayList<>());
    p21 = productRepository.save(p21);

    Characteristic c211 = new Characteristic(null, "Type", "Plush", p21);
    c211 = characteristicRepository.save(c211);

    Product p22 = new Product(null, "Scratching post",
        "Scratching post Fluffy with sofa 2 floors Gray with beige 30x38x50 cm",
        755D, c2, imageService.getImageByPath("23690798.jpg"), new ArrayList<>());
    p22 = productRepository.save(p22);

    Characteristic c212 = new Characteristic(null, "Type", "Claws (scratches)", p22);
    c212 = characteristicRepository.save(c212);

    Product p23 = new Product(null, "GiGwi Bird",
        "Toy for cats GiGwi Bird with sound chip and catnip Melody chaser 13 cm",
        219D, c2, imageService.getImageByPath("245519002.jpg"), new ArrayList<>());
    p23 = productRepository.save(p23);

    Characteristic c213 = new Characteristic(null, "Type", "Interactive toys", p23);
    c213 = characteristicRepository.save(c213);

    Product p24 = new Product(null, "Roly poly Snack egg interactive",
        "Toy for dogs Trixie Roly poly Snack egg interactive educational plastic 13 cm",
        388D, c2, imageService.getImageByPath("294266762.jpg"), new ArrayList<>());
    p24 = productRepository.save(p24);

    Characteristic c214 = new Characteristic(null, "Type", "Interactive toys", p24);
    c214 = characteristicRepository.save(c214);

    Basket b1 = new Basket(new BasketKey(), firstCustomer, p11, 5);
    b1 = basketRepository.save(b1);

    Basket b2 = new Basket(new BasketKey(), firstCustomer, p12, 3);
    b2 = basketRepository.save(b2);

    Basket b3 = new Basket(new BasketKey(), firstCustomer, p23, 1);
    b3 = basketRepository.save(b3);

    System.out.println("Added first user: " + firstAdmin);
    System.out.println("Added second user: " + firstManager);
    System.out.println("Added third user: " + firstCustomer);
    System.out.println("===================================");
    System.out.println("Added category: " + c1);
    System.out.println("Added product: " + p11);
    System.out.println("Added review: " + r111);
    System.out.println("Added review: " + r112);
    System.out.println("Added comment: " + co111);
    System.out.println("Added question: " + q111);
    System.out.println("Added question: " + q112);
    System.out.println("Added comment: " + co112);
    System.out.println("Added product: " + p12);
    System.out.println("Added product: " + p13);
    System.out.println("Added product: " + p14);
    System.out.println("===================================");
    System.out.println("Added category: " + c2);
    System.out.println("Added product: " + p21);
    System.out.println("Added product: " + p22);
    System.out.println("Added product: " + p23);
    System.out.println("Added product: " + p24);
    System.out.println("===================================");
    System.out.println("Added category: " + c3);
    System.out.println("===================================");
    System.out.println("Added category: " + c4);
    System.out.println("===================================");
    System.out.println("Added category: " + c5);
    System.out.println("===================================");
    System.out.println("Added product to firstCustomer's basket: " + b1);
    System.out.println("Added product to firstCustomer's basket: " + b2);
    System.out.println("Added product to firstCustomer's basket: " + b3);
  }
}
