package io.github.chenfh5

import java.security.MessageDigest
import java.text.SimpleDateFormat

import scala.collection.mutable.ListBuffer


class BlockchainService {
  var blockchain = ListBuffer[Block]()

  blockchain.append(getFristBlock)

  def calculateHash(block: Block) = {
    val record = Seq(block.index, block.timeStamp, block.PreHash, block.ownData).mkString("")
    getSHA256(record)

  }

  def generateBlock(oldBlock: Block, ownData: String) = {
    val hashed = calculateHash(oldBlock)
    Block(oldBlock.index + 1, getTsNow, oldBlock.Hash, calculateHash(oldBlock), ownData)
  }

  def isValidBlock(oldBlock: Block, newBlock: Block): Boolean = {
    if (oldBlock.index + 1 != newBlock.index) return false
    if (oldBlock.Hash != newBlock.PreHash) return false
    if (calculateHash(oldBlock) != newBlock.Hash) return false
    true
  }

  def replaceChain(newBlockchain: ListBuffer[Block]) = {
    if (newBlockchain.size > blockchain.size) blockchain = newBlockchain
  }

  def getSHA256(str: String) = {
    MessageDigest.getInstance("SHA-256")
        .digest(str.getBytes("UTF-8"))
        .map("%02x".format(_)).mkString
  }

  def getTsNow = {
    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS E").format(System.currentTimeMillis)
  }

  def add(newBlock: Block) = {
    if (isValidBlock(getLatestBlock, newBlock)) blockchain += newBlock
    else println(s"newBlock can not add $newBlock")
  }

  def getLatestBlock = {
    blockchain.last
  }

  def getFristBlock = {
    Block(0, getTsNow, "", "", "I am genesis block")
  }

}

case class Block(
  index: Int,
  timeStamp: String,
  PreHash: String,
  Hash: String,
  ownData: String // own business
)
