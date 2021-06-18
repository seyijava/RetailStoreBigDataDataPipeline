package com.bigdataconcept.retail.sales.simulator

import com.bigdataconcept.retail.sales.simulator.Domain.ItemSale
import com.bigdataconcept.retail.sales.simulator.CategoryType.{Women, Men,Kid,CategoryType}
import org.scalacheck.Gen

object CategoryType extends Enumeration {
  type CategoryType = Value
  val Men,Women,Kid = Value
}



object SaleDataGenerator {



  val productCode = Gen.choose(20, 100)

  val quantity  = Gen.choose(1, 2)

  val categoryType = Gen.frequency(2 -> Men, 3 -> Women, 5 -> Kid)

  val kidItemCost = Gen.choose(5,20)

  val menItemCost = Gen.choose(15, 250)

  val womenItemCost = Gen.choose(15 , 550)


  def getItemCost(categoryType: CategoryType) = categoryType match {
    case  Men => menItemCost
    case Women => womenItemCost
    case Kid => kidItemCost
  }

  private val menItem = Gen.frequency(
    1 -> ("P-001","SAINT LAURENT Sun Glasses"),
    1 -> ("P-002","GUCCI Sun Glasses"),
    3 -> ("P-001","SAINT LAURENT Sun Glasses"),
    6 -> ("P-003","GUCCI Sun Glasses"),
    3 -> ("P-004","BVLGARI Man Eau De Toilette"),
    6 -> ("P-005","BOND NO. 9 New York Amber Eau De Parfume"),
    5 -> ("P-006","POLO RALPH LAUREN Men's Moccasin Slippers"),
    2 -> ("P-007","BALLY Men's Made In Italy Leather Designer Belt"),
    3 -> ("P-008","BOTTEGA VENETA Men's Made In France 1.7oz Pour Home Eau De Toilette"),
    4 -> ("P-009","VIVITAR 3-head Rotary Shaver & Ear And Nose Trimmer"),
    1 -> ("P-010","SBASS Men's Wave Style Pure Bristle Firm Brush"),
    3 -> ("P-011","NAUTICA Men's Sport Slip On Sneakers"),
    6 -> ("P-012","SRBX Athletic Sneakers"),
    3 -> ("P-013","FILA Athletic Slides"),
    1 -> ("P-014","ARMANI EXCHANGE Men's Slide Sandals"),
    3 -> ("P-015","MERRELL Sport Sandals"),
    5 -> ("P-016","TIMBERLAND Waterproof Chukka Boots"),
    6 -> ("P-017","COLE HAAN Men's Leather Wingtip Oxfords"),
    3 -> ("P-018","ROCKPORT Men's Suede Venetian Flats"),
    2 -> ("P-019","CLARKS UNSTRUCTURED Men's Sport Casual Joggers"),
    1 -> ("P-020","TRUE RELIGION Camo Logo Joggers"),


    1 -> ("P-021","TRUE RELIGION True Logo Crew Neck Tee"),
    1 -> ("P-022","ROBERTO CAVALLI Logo Crew Neck Sweatshirt"),
    3 -> ("P-023","CHAMPION Heritage Long Sleeve Tee"),
    6 -> ("P-024","CALVIN KLEIN JEANS Foil Monogram Tee"),
    3 -> ("P-025","REEBOK Vector Outline Graphic Tee"),
    6 -> ("P-026","KARL LAGERFELD PARIS Latitude & Longitude Tee"),
    5 -> ("P-027","DIESEL  Lebed Military Moleskin Overshirt"),
    2 -> ("P-028","US POLO ASSOCIATION Short Sleeve Pique Classic Polo"),
    3 -> ("P-029","JUST CAVALLI Just T-shirt"),
    4 -> ("P-030","UNDER ARMOUR Sportstyle Logo Graphic Long Sleeve Tee"),
    1 -> ("P-031","POLO RALPH LAUREN 3pk Flat Knit Sneaker Liner Socks"),
    3 -> ("P-032","US POLO ASSOCIATION Big And Tall French Terry Shorts"),
    6 -> ("P-033","TRUE RELIGION Ricky Big T Shorts With Raw Hem"),
    3 -> ("P-034","DANIEL HECHTER Comfort Shorts"),
    1 -> ("P-035","LUCKY BRAND Printed Cargo Shorts"),
    3 -> ("P-036","TRUE RELIGION Box Foil Logo Crew Neck Sweatshirt"),
    5 -> ("P-037","KARL LAGERFELD PARIS Leather Moto Jacket"),
    6 -> ("P-038","HUGO BOSS Landolfo Quilted Shirt Jacket"),
    3 -> ("P-039","RAG & BONE Manston Bomber Jacket"),
    2 -> ("P-040","BURBERRY nMen's Made In Italy Leather Dress Shoes"),
  )


  private val womenItem = Gen.frequency(
    1 -> ("P-041","OLIVIA MILLER Flat Sandals"),
    5 -> ("P-042","OLIVIA MILLER Flat Sandals"),
    2 -> ("P-043","ROCKPORT Leather Comfort Sandals"),
    4 -> ("P-044","CUSHIONAIRE Comfort Footbed Sandals"),
    1 -> ("P-045","STEVE MADDEN Flat Studded Sandals"),
    2 -> ("P-046","BDONALD PLINER Suede Comfort Slip-on Mules"),
    4 -> ("P-047","SOLUDOS Platform Espadrille Flats"),
    2 -> ("P-048","GUCCI Gg Matelasse Leather Shoulder Bag"),
    3 -> ("P-049","BRACCIALINI Greetings Shoulder Bag"),
    2 -> ("P-050","VTHE NORTH FACE Pivoter Backpack"),
    5 -> ("P-051","SANDREA CARDONE Leather Crossbody With Chain"),
    2 -> ("P-052","VALENTINO BY MARIO VALENTINO Leather Vanille Quilted Shoulder Bag"),
    6 -> ("P-053","PERSAMAN NEW YORK Leather Star Detail Satchel"),
    4 -> ("P-054","ROSSI Leather Woven Bucket Bag"),
    1 -> ("P-055","KASPER Stretch Crepe Combo Seamed Sleeveless Sheath Dress"),
    3 -> ("P-056","DONNA MORGAN Fit And Flare Dress With Waist Sash"),
    5 -> ("P-057","LE SUIT Stretch Crepe Jacket And Flounce Skirt Set"),
    4 -> ("P-058","TRINA TURK Camp Stripe Detail Dress"),
    3 -> ("P-059","SHARAGANO Tie Front Polka Dot Dress"),
    2 -> ("P-060","TAHARI ASL Side Ruched Dress With Long Sleeve"),
    1 -> ("P-061","TTAHARI ASL Side Ruch Dress"),


    1 -> ("P-062","CLARINS 1.7oz Multi-active Jour Day Cream"),
    1 -> ("P-063","LANEIGE 2.3oz Waterbank Moisturizing Essence"),
    3 -> ("P-064","COSRX 3.38oz Aha 7 Whitehead Liquid"),
    4 -> ("P-065","CSOME BY  4.23oz Blackhead Bubble Cleanser"),
    3 -> ("P-066","NCLA Hey Doll Nail Lacquer"),
    6 -> ("P-067","OPI Natural Nail Base Coat"),
    5 -> ("P-068","ORIBE Conditioner For Beautiful Color"),
    2 -> ("P-069","ELLE Heated Ion Straightening Ceramic Hair Brush"),
    3 -> ("P-070","THEORIE Argan Oil Reforming Conditioner"),
    4 -> ("P-071","FEKKAI The one Vibrance Boosting Color Mask"),
    1 -> ("P-072","LAURA GELLER Deep Soft Matte Bronzer"),
    3 -> ("P-073","ADRIANNA PAPELL Sequin Crepe Dress"),
    6 -> ("P-074","KAY UNGER Valentina Gown"),
    3 -> ("P-075","AIDAN MATTOX Liquid Satin Draped Gown"),
    1 -> ("P-076","TADASHI SHOJI Corded Lace & Taffeta Skirt Gown"),
    3 -> ("P-077","STATE Flutter Sleeve Jumpsuit"),
    5 -> ("P-078","MARINA Made In Usa Sleeveless Jumpsuit"),
    6 -> ("P-079","VELVET HEART Ceana Popover Linen Blend Shirt Dress"),
    3 -> ("P-080","HALSTON Sleeveless Linen Shirt Dress"),
    2 -> ("P-081","MAX STUDIO Textured Sleeveless Tiered Dress"),



  )



  private val kidItem = Gen.frequency(
    5 -> ("P-082","FILA Big Boy Mesh Performance Shorts"),
    1 -> ("P-083","UNDER ARMOUR Boys Tech Cross Fade Tee"),
    3 -> ("P-084","KAMIK Electro Sandals (Little Kid, Big Kid)"),
    5 -> ("P-085","NAUTICA Sneakers (Toddler)"),
    4 -> ("P-086","JOULES Shore Printed Footbed Slider Sandals"),
    6 -> ("P-087","EMU Suede Lion Pull On Boots"),
    5 -> ("P-088","GEOX Leather Comfort Moccasins"),
    2 -> ("P-089","NEW BALANCE Wide Lifestyle Sneakers"),
    3 -> ("P-090","SPERRY Slip On Leather Boat Shoes "),
    4 -> ("P-091","SLEEP ON IT Baby Boys Dino Sleep Set With Socks"),
    1 -> ("P-092","DKNY Big Boy Ripstop Stretch Twill Shorts"),
    3 -> ("P-093","TOMMY BAHAMA Toddler Boys 4pc Mix & Match Dinosaur Short Set"),
    3 -> ("P-094","BODY GLOVE Little Boy 3pc Active Shorts Set"),
    2 -> ("P-095","TIMBERLAND Big Boy Reflective Tree Tee"),
    1 -> ("P-096","TRUE RELIGION Little Boy Gold Buddha Logo Tee"),
    4 -> ("P-097","PUMA Little Boy 2pk Active Shorts"),
    1 -> ("P-098","COPPER DENIM Little Boy 4pk Graphic Tees"),
    3 -> ("P-099","CBROOKLYN CLOTH Toddler Boy Good Vibes Checkerboard Shorts Set"),
    5-> ("P-100","AUTHENTIC KIDS Boys Rainbow Inclusivity Striped Tee"),
    6 -> ("P-101","REEBOK Toddler Boy 3pc Lounge Short Set"),
    4 -> ("P-102","LEVIS Big Boy Batwing Logo Tee"),


    5 -> ("P-103","JOULES Flamingo Flip Flops"),
    2 -> ("P-104","MIA Glitter Sandals"),
    3 -> ("P-105","GIULIAPALAI Velcro Glitter Sandals"),
    6 -> ("P-106","CROCS Molded Slip On Clog Flats"),
    4 -> ("P-107","OLIVIA MILLER Omg Double Buckle With Ankle Strap Sandals"),
    3 -> ("P-108","NINA Leather Metallic Faux Fur Sneakers"),
    2-> ("P-109","MERRELLPanther Sandals"),
    3 -> ("P-110","NAUTICA Mesh Sneakers"),
    3 -> ("P-111","JPICAPINO Little Girls Polka Dot Dress"),
    4 -> ("P-112","ULILY BLEU Big Girls Tie Front Flip Sequin Top With Scrunchie"),
    1 -> ("P-113","YOUNG HEARTS Little Girls Striped Romper"),
    3 -> ("P-114","YOUNG HEARTS Toddler Girls 2pk Romper Set With Headband"),
    6 -> ("P-115","THE PRAIRIE BY RACHEL ASHWELLToddler Girls Floral Dress With Headband"),
    3 -> ("P-116","PENELOPE MACK Toddler Girls Embroidered Dress"),
    1 -> ("P-117","DKNY Big Girls Denim Romper"),
    3 -> ("P-118","JUSTICE Big Girls Acid Wash Skirt"),
    5 -> ("P-119","7 FOR ALL MANKIND Infant Girls 2pc Tie Dye Top And Short Set"),
    6 -> ("P-120","HUGO BOSS Landolfo Quilted Shirt Jacket"),
    3 -> ("P-121","PLAYGO 28pc Gourmet Ice Cream Cart"),
    2 -> ("P-122","PLAYGO 10pc Shape-a-barn"),

  )

  def itemGen: Gen[Item] =
    for {
      category <- categoryType
      (code,itemDescription) <- getItemCategory(category)
      cost <- getItemCost(category)
      qty <- quantity
    } yield Item( code ,itemDescription, cost, category.toString,qty)

  def getItemCategory(categoryType: CategoryType) = categoryType match {
      case Women => womenItem
      case Men => menItem
      case Kid => kidItem
    }

   def generatorSaleItem: ItemSale = {
      val item =  itemGen.sample.get
      ItemSale(item.itemCode, item.description,item.qty, item.price,  item.category)
   }

  case class Item(itemCode: String, description: String, price: Double, category: String, qty: Int)
}
